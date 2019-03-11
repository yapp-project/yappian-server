package com.yapp.web1.domain;


import com.yapp.web1.common.RepositoryTest;
import com.yapp.web1.domain.VO.ProjectType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class ConverterTest extends RepositoryTest {
    @PersistenceContext
    private EntityManager em;

    @Before
    public void setup(){
        Orders orders = Orders.builder().number(14).build();
        em.persist(orders);

        Project project = Project.builder()
                .type(ProjectType.WEB)
                .name("프로젝트팀")
                .createUserIdx(1L)
                .orders(orders).build();
        em.persist(project);
        em.flush();
        em.clear();
    }

    @Test
    @Transactional
    public void ProjectTypeConverter변환테스트(){
        Query query = em.createNativeQuery("select * from project where type = :type", Project.class);
        query.setParameter("type", 0); // WEB is 0
        List<Project> projectList = query.getResultList();

        // confirm
        assertThat(ProjectType.WEB, is(projectList.get(0).getType()));
    }
}
