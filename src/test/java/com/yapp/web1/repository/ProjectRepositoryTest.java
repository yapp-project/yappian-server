package com.yapp.web1.repository;

import com.yapp.web1.common.RepositoryTest;
import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.ProjectType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class ProjectRepositoryTest extends RepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    public void BaseEntity테스트(){
        // given
        LocalDateTime now = LocalDateTime.now();

        Orders orders = Orders.builder().number(14).build();
        Orders savedOrders = ordersRepository.save(orders);

        Project project = Project.builder()
                .type(ProjectType.WEB)
                .name("프로젝트팀")
                .createUserIdx(1L)
                .orders(savedOrders).build();
        projectRepository.save(project);

        // when
        List<Project> projectList = projectRepository.findAll();

        // then
        Project savedProject = projectList.get(0);
        assertTrue(savedProject.getCreateDate().isAfter(now));
        assertTrue(savedProject.getModifiedDate().isAfter(now));
    }

}
