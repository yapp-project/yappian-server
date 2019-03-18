package com.yapp.web1.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 프로젝트 완료 페이지 작성 Dto
 * @author JiHye Kim
 * @author Dakyung Ko
 */
@NoArgsConstructor
@Setter
@Getter
public class FinishProjectRequestDto {
    @NotBlank(message = "프로젝트 설명을 입력하세요.")
    @Size(min = 3)
    private String description; // 프로젝트 설명

    private String productURL; // 바로가기 URL
    // 첨부파일 List - 추후 수정

    @Builder
    public FinishProjectRequestDto(String description, String productURL){
        this.description = description;
        this.productURL = productURL;
    }
}
