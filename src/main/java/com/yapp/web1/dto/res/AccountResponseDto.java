package com.yapp.web1.dto.res;

import lombok.Builder;
import lombok.Getter;

/**
 * Account ResponseDto
 *
 * @author Dakyung Ko
 */
@Getter
public class AccountResponseDto {
    private Long accountIdx;
    private String name;

    @Builder
    public AccountResponseDto(Long accountIdx, String name){
        this.accountIdx = accountIdx;
        this.name = name;
    }
}
