package com.yapp.web1.service;

import com.yapp.web1.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

//    public List<ClubOrder> findOrder(){
//        return orderRepository.findByNum();
//    }
}
