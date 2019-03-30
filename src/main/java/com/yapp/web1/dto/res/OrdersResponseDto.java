package com.yapp.web1.dto.res;

import com.yapp.web1.domain.Orders;
import lombok.Builder;
import lombok.Getter;

/**
 * 기수 목록 Dto
 * @author JiHye Kim
 */
@Getter
public class OrdersResponseDto {

    private Long idx;
    private int number;

    @Builder
    public OrdersResponseDto(Orders orders){
        this.idx = orders.getIdx();
        this.number = orders.getNumber();
    }
}
