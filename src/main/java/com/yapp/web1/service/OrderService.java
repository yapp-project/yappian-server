package com.yapp.web1.service;

import com.yapp.web1.domain.Order;
import com.yapp.web1.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> findOrder(){
        return orderRepository.findByNum();
    }
}
