package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.dto.res.OrdersResponseDto;
import com.yapp.web1.dto.res.ProjectListResponseDto;
import com.yapp.web1.repository.OrdersRepository;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * OrdersService 구현 클래스
 *
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

    // create order
    @Scheduled(cron="0 0 0 0 0/6 * * ")
    @Override
    public void createOrder(){
        List<Orders> findOrders = ordersRepository.findByOrderByNumberDesc();

        int number = findOrders.get(0).getNumber();

        Orders orders = Orders.builder()
                .number(++number)
                .build();

        ordersRepository.save(orders);
    }

    // get orderList
    @Transactional(readOnly = true)
    @Override
    public List<OrdersResponseDto> getOrderList() {
       List<Orders> findOrders = ordersRepository.findByOrderByNumberDesc();
       List<OrdersResponseDto> orderList = new ArrayList<>();
       for(Orders orders : findOrders)
           orderList.add(new OrdersResponseDto(orders));
        return orderList;
    }

    // get projectList by order
    @Transactional(readOnly = true)
    @Override
    public List<ProjectListResponseDto> getProjectListByOrder(Long orderIdx) {
        List<Project> findProjects = projectRepository.findByOrdersIdxAndFinalCheck(orderIdx,Mark.Y);

        List<ProjectListResponseDto> projectList = new ArrayList<>();

        for(Project project : findProjects) {
            ProjectListResponseDto projectDto = ProjectListResponseDto.builder()
                    .projectIdx(project.getIdx())
                    .type(project.getType())
                    .name(project.getName())
                    .releaseCheck(project.getReleaseCheck())
                    .imgUrl(project.getFileList().get(0).getFileURL())
                    .build();
            projectList.add(projectDto);
        }
        return projectList;
    }
}
