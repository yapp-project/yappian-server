package com.yapp.web1.service;

import com.yapp.web1.domain.Order;
import com.yapp.web1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> findOrder(){
        return orderRepository.findByNum();
    }
}
