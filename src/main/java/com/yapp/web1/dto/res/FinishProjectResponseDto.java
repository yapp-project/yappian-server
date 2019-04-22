package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.ProjectType;
import lombok.Builder;
import lombok.Getter;

/**
 * 프로젝트 완료 페이지 Dto
 *
 * @author Dakyung Ko
 */
@Getter
public class FinishProjectResponseDto {
    private int ordersNumber; // 기수
    private ProjectType projectType; // 프로젝트 타입
    private String projectName; // 프로젝트 이름
    private String projectDescription; // 프로젝트 설명
    private String productURL; // 바로가기 URL

    @Builder
    public FinishProjectResponseDto(int ordersNumber, ProjectType projectType, String projectName,
                                String projectDescription, String productURL){
        this.ordersNumber = ordersNumber;
        this.projectType = projectType;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.productURL = productURL;
    }
}
