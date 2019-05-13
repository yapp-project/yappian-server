package com.yapp.web1.dto.req;

import com.yapp.web1.domain.VO.Mark;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * 프로젝트 완료 페이지 작성 Dto
 * @author JiHye Kim
 * @author Dakyung Ko
 */
@NoArgsConstructor
@Setter
@Getter
@ApiModel(description = "File 생성에 사용하는 모델")
public class FinishProjectRequestDto {

    @NotBlank(message = "프로젝트 설명을 입력하세요.")
    @ApiModelProperty(notes = "프로젝트 상세 설명. 최소 30자")
    @Size(min = 30)
    private String description; // 프로젝트 소개

    @NotBlank(message = "프로젝트 URL을 입력하세요.")
    @ApiModelProperty(notes = "프로젝트 대표 URL")
    private String productURL; // 프로젝트 URL

    @NotNull(message = "런칭 유무를 체크해 주세요")
    @ApiModelProperty(notes = "런칭 유무 (N,Y) (순서대로 0,1도 가능)")
    private Mark releaseCheck = Mark.N;

    @Builder
    public FinishProjectRequestDto(String description, String productURL, Mark releaseCheck){
        this.description = description;
        this.productURL = productURL;
        this.releaseCheck = releaseCheck;
    }
}
