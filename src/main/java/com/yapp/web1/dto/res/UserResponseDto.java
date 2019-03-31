package com.yapp.web1.dto.res;

import lombok.Builder;
import lombok.Getter;

/**
 * User ResponseDto
 *
 * @author Dakyung Ko
 */
@Getter
public class UserResponseDto {
    private Long userIdx;
    private String name;

    @Builder
    public UserResponseDto(Long userIdx, String name){
        this.userIdx = userIdx;
        this.name = name;
    }
}
