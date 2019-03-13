package com.yapp.web1.dto.res;


import com.yapp.web1.domain.Orders;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 기수 목록 Dto
 * @author JiHye Kim
 */
@Getter
public class OrdersResponseDto {

    private Long idx;
    private int number;

    @Builder
    public OrdersResponseDto(Long idx, int number){
        this.idx = idx;
        this.number = number;
    }
}
