package com.yapp.web1.controller;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Orders Controller
 *
 * @author Dakyung Ko, JiHye Kim
 * @since 0.0.3
 * @version 1.0
 */
//@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class OrderController {

     @Autowired  OrderService orderService;// = new OrderService;
    /**
     * 기수 목록 리스트
     *
     * @return 기수 목록 list
     *
     * @see /v1/api/orders
     */
    @GetMapping("/orders")
    public ResponseEntity<List<Orders>> getOrderList(){
        List<Orders> orders = orderService.findNumber();
        return new ResponseEntity<>(orders, HttpStatus.OK);//200. (요청이 성공적으로 되었습니다.리소스를 불러와서 메시지 바디에 전송되었습니다.)
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
