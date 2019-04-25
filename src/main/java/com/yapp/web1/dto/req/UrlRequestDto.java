package com.yapp.web1.dto.req;

import com.yapp.web1.domain.VO.UrlType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UrlRequestDto {

    private Long projectIdx;
    private UrlType type;
    private String title;
    private String contents;

    @Builder
    public UrlRequestDto(Long projectIdx, UrlType type, String title, String contents){
        this.projectIdx = projectIdx;
        this.type = type;
        this.title = title;
        this.contents = contents;
    }
}
