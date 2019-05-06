package com.yapp.web1.controller;

import com.yapp.web1.dto.res.OrdersResponseDto;
import com.yapp.web1.dto.res.ProjectListResponseDto;
import com.yapp.web1.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Orders Controller
 *
 * @author JiHye Kim
 * @since 0.0.3
 * @version 1.2
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class OrdersController {

    private OrdersService ordersService;

    /**
     * 기수 목록 리스트
     *
     * @return 기수 목록 list
     *
     * @see /v1/api/orders
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrdersResponseDto>> getOrderList(){
        List<OrdersResponseDto> orders = ordersService.getOrderList();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * 기수별 프로젝트 목록 - finalCheck가 Y인 프로젝트만 조회
     *
     * @param orderIdx 조회할 기수의 idx
     * @param session 로그인 유저 정보
     * @return 기수별 프로젝트 list
     *
     * @see /v1/api/order/{orderIdx}/projects
     */
    @GetMapping("/order/{orderIdx}/projects")
    public ResponseEntity<List<ProjectListResponseDto>> getProjectListByOrder(@PathVariable final Long orderIdx, HttpSession session){
        List<ProjectListResponseDto> projectList = ordersService.getProjectListByOrder(orderIdx);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }
}
