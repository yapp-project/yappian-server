package com.yapp.web1.service;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.dto.res.OrdersResponseDto;
import com.yapp.web1.dto.res.ProjectListResponseDto;

import java.util.List;
import java.util.Set;

/**
 * OrdersService Interface
 *
 * @author Dakyung Ko
 * @author JiHye Kim
 * @since 0.0.3
 * @version 1.2
 */
public interface OrdersService {
    /**
     * 기수 목록 반환
     *
     * @return 전체 기수 list
     */
    List<OrdersResponseDto> getOrderList();

    /**
     * 기수별 프로젝트 목록
     *
     * @param idx 조회할 Orders(기수)의 idx
     * @return 해당 기수의 프로젝트 list
     */
    List<ProjectListResponseDto> getProjectListByOrder(Long orderIdx);
}
