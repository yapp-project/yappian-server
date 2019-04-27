package com.yapp.web1.dto.req;

import com.yapp.web1.domain.VO.UrlType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UrlRequestDto {

    private UrlType type;
    private String title;
    private String contents;

    @Builder
    public UrlRequestDto(UrlType type, String title, String contents){
        this.type = type;
        this.title = title;
        this.contents = contents;
    }
}
