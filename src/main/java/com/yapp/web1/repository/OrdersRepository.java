package com.yapp.web1.repository;

import com.yapp.web1.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByOrderByNumberDesc();
    Orders findByNumber(int number);
}
