package com.yapp.web1.repository;

import com.yapp.web1.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long> {
//    pubilc List<Orders> findByOrderByNumDesc();
}
