package com.yapp.web1.dto.res;

import lombok.Builder;
import lombok.Getter;

/**
 * Comment ResponseDto
 *
 * @author Dakyung Ko
 */
@Getter
public class CommentResponseDto {
    private UserResponseDto user;
    private String contents;

    @Builder
    public CommentResponseDto(UserResponseDto user, String contents){
        this.user = user;
        this.contents = contents;
    }
}
