package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.UrlType;
import lombok.Builder;
import lombok.Getter;

/**
 * Url 정보 res dto

 * @author JiHye Kim
 */
@Getter
public class UrlResponseDto {

    private Long idx;
    private UrlType type;
    private String title;
    private String contents;

    @Builder
    public UrlResponseDto(Long idx, UrlType type, String title, String contents){
        this.idx = idx;
        this.type = type;
        this.title = title;
        this.contents = contents;
    }
}
