package com.yapp.web1.domain;

import com.yapp.web1.common.RepositoryTest;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.ProjectType;
import com.yapp.web1.repository.OrdersRepository;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ConverterTest extends RepositoryTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrdersRepository ordersRepository;

    @Before
    public void setup(){
        Orders orders = ordersRepository.findByNumber(14);
        em.persist(orders);

        Project project = Project.builder()
                .type(ProjectType.WEB)
                .password("passwd")
                .name("프로젝트팀")
                .finalCheck(Mark.N)
                .releaseCheck(Mark.N)
                .createUserIdx(1L)
                .orders(orders)
                .build();
        em.persist(project);
        em.flush();
        em.clear();
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void ProjectTypeConverter변환테스트(){
        // Native Query로 테스트해야하기 때문에 unchecked함
        Query<Project> query = (Query<Project>) em.createNativeQuery("select * from project where type = :type", Project.class);
        query.setParameter("type", 0); // WEB is 0
        List<Project> projectList = query.getResultList();

        // confirm
        assertThat(ProjectType.WEB, is(projectList.get(0).getType()));
    }
}
