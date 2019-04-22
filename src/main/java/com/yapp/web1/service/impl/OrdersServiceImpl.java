package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.dto.res.OrdersResponseDto;
import com.yapp.web1.dto.res.ProjectListResponseDto;
import com.yapp.web1.repository.OrdersRepository;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * OrdersService 구현 클래스
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.1
 */
@Service
@Transactional
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    @Override
    public List<OrdersResponseDto> getOrderList() {
       List<Orders> findOrders = ordersRepository.findByOrderByNumberDesc();
       List<OrdersResponseDto> orderList = new ArrayList<>();
       for(Orders orders : findOrders)
           orderList.add(new OrdersResponseDto(orders));
        return orderList;
    }

    /*
     favorite과 joined는 추후,,
     */
    @Transactional(readOnly = true)
    @Override
    public List<ProjectListResponseDto> getProjectListByOrder(Long orderIdx) {
        List<Project> findProjects = projectRepository.findAllByOrdersIdx(orderIdx);
        List<ProjectListResponseDto> projectList = new ArrayList<>();
        for(Project project : findProjects) {
            ProjectListResponseDto projectDto = ProjectListResponseDto.builder()
                    .projectIdx(project.getIdx())
                    .projectType(project.getType())
                    .projectName(project.getName())
                    .finalCheck(project.getFinalCheck())
                    .createUserIdx(project.getCreateUserIdx())
                    .orderNumber(project.getOrders().getNumber())
                    .favorite(null) // 수정해야함
                    .joined(null) // 수정해야함
                    .build();
            projectList.add(projectDto);
        }
        return projectList;
    }
}
