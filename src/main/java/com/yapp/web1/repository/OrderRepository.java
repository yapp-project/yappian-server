package com.yapp.web1.repository;

import com.yapp.web1.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    pubilc List<Order> findByOrderByNumDesc();
}
