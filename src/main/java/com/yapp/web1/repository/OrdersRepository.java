package com.yapp.web1.repository;

import com.yapp.web1.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
    Orders findByNumber(int number);
}
