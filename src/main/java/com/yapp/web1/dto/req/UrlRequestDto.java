package com.yapp.web1.dto.req;

import com.yapp.web1.domain.VO.UrlType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Url 생성시 RequestDto
 *
 * @author JiHye Kim
 */

@NoArgsConstructor
@Setter
@Getter
@ApiModel(description = "Url 생성 또는 수정에 사용하는 모델")
public class UrlRequestDto {

    @NotNull
    @ApiModelProperty(notes = "URL 타입 (TOOL,FIRST, SECOND, HOME) (순서대로 숫자 0,1,2,3도 가능)")
    private UrlType type;

    @NotNull
    @ApiModelProperty(notes = "URL 제목")
    private String title;
    @NotNull
    @ApiModelProperty(notes = "URL 내용(링크)")
    private String contents;

    @Builder
    public UrlRequestDto(UrlType type, String title, String contents){
        this.type = type;
        this.title = title;
        this.contents = contents;
    }
}
