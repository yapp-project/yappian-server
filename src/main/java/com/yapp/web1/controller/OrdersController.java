package com.yapp.web1.controller;

import com.yapp.web1.dto.res.OrdersResponseDto;
import com.yapp.web1.dto.res.ProjectListResponseDto;
import com.yapp.web1.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Orders Controller
 *
 * @author Dakyung Ko
 * @author JiHye Kim
 * @since 0.0.4
 * @version 1.3
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api")
@RestController
@Api(tags = "기수 APIs")
public class OrdersController {

    private OrdersService ordersService;

    /**
     * 기수 목록 리스트
     *
     * @return 기수 목록 list
     *
     * @see /api/orders
     */
    @GetMapping("/orders")
    @ApiOperation(value = "기수 목록 호출 (인증 필요 없음)")
    public ResponseEntity<List<OrdersResponseDto>> getOrderList(){
        List<OrdersResponseDto> orders = ordersService.getOrderList();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * 기수별 프로젝트 목록 - finalCheck가 Y인 프로젝트만 조회
     *
     * @param orderIdx 조회할 기수의 idx
     * @return 기수별 프로젝트 list
     *
     * @see /api/order/{orderIdx}/projects
     */
    @GetMapping("/order/{orderIdx}/projects")
    @ApiOperation(value = "기수별 프로젝트 목록 - finalCheck가 Y인 프로젝트만 조회 (인증 필요 없음)")
    public ResponseEntity<List<ProjectListResponseDto>> getProjectListByOrder(@PathVariable  @ApiParam(value = "조회할 orders idx", example = "1")final Long orderIdx){
        List<ProjectListResponseDto> projectList = ordersService.getProjectListByOrder(orderIdx);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }
}
