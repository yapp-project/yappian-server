package com.yapp.web1.controller;

import com.yapp.web1.domain.Order;
import com.yapp.web1.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class OrderController {

    private final OrderService orderService;

    //기수 목록 리스트
    @GetMapping("orderList")
    public List<Order> getOrderList(){
        return orderService.findOrder();
    }

}
