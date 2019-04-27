package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.UrlType;
import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
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
