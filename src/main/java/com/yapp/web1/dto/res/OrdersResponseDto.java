package com.yapp.web1.dto.res;

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
    public OrdersResponseDto(Long idx, int number){
        this.idx = idx;
        this.number = number;
    }
}
