package com.yapp.web1.controller;

import com.yapp.web1.dto.res.OrdersResponseDto;
import com.yapp.web1.dto.res.ProjectListResponseDto;
import com.yapp.web1.service.OrdersService;
import com.yapp.web1.service.impl.OrdersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * Orders Controller
 *
 * @author Dakyung Ko, JiHye Kim
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
     * 기수별 프로젝트 목록
     *
     * @param idx 조회할 기수의 idx
     * @return 기수별 프로젝트 list
     * @exception Exception favorite과 joined는 userIdx가 필요할것같음. 일단 userIdx없이 프로젝트리스트를 뽑아오겠음.
     * @see /v1/api/order/{idx}
     */
    @GetMapping("/order/{idx}")
    public ResponseEntity<List<ProjectListResponseDto>> getProjectListByOrder(@PathVariable final Long idx){
        List<ProjectListResponseDto> projectList = ordersService.getProjectListByOrder(idx);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }
}
