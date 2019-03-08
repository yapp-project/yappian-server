package com.yapp.web1.service;

import com.yapp.web1.domain.Orders;
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

   @Autowired
   OrderRepository orderRepository;

    public List<Orders> findNumber(){
        return orderRepository.findByOrderByNumberDesc();
    }
}
