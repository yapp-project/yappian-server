package com.yapp.web1.repository;

import com.yapp.web1.domain.ClubOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ClubOrder,Long> {
//    pubilc List<ClubOrder> findByOrderByNumDesc();
}
