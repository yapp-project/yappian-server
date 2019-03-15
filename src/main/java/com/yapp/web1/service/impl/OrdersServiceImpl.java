package com.yapp.web1.service.impl;

import com.yapp.web1.dto.res.OrdersResponseDto;
import com.yapp.web1.dto.res.ProjectListResponseDto;
import com.yapp.web1.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * OrderService 구현 클래스
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.0
 */
@Service
@Transactional
@AllArgsConstructor
public class OrdersServiceImpl implements OrderService {

    @Transactional(readOnly = true)
    @Override
    public List<OrdersResponseDto> getOrderList() {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectListResponseDto> getProjectListByOrder(Long idx) {
        return null;
    }
}
