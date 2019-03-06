package com.yapp.web1.controller;

import com.yapp.web1.domain.Order;
import com.yapp.web1.domain.Project;
import com.yapp.web1.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Order Controller
 *
 * @author Dakyung Ko
 * @since 0.0.2
 * @version 1.0
 */
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class OrderController {

    private final OrderService orderService;

    /**
     * 기수 목록 리스트
     *
     * @return 기수 목록 list
     *
     * @see /v1/api/orders
     */
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrderList(){
//        return orderService.findOrder();
        List<Order> orders = new ArrayList<>();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * 기수별 프로젝트 목록
     *
     * @param idx 조회할 기수의 idx
     * @return 기수별 프로젝트 list
     *
     * @see /v1/api/order/{idx}
     */
    @GetMapping("/order/{idx}")
    public ResponseEntity<List<Project>> getProjectListByOrder(@PathVariable final Long idx){
//        return projectService.findProjectByOrder();
        List<Project> projectList = new ArrayList<>();
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }
}
