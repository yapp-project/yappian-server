package com.yapp.web1.dto.res;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.VO.ProjectType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 프로젝트 생성하는 페이지 Get ResponseDto
 * @author JiHye Kim
 */

@Getter
public class ProjectSaveResponseDto {
    private List<Orders> orders;// 기수 목록
    private List<ProjectType> projectTypes;// 프로젝트 타입 목록

    @Builder
    public ProjectSaveResponseDto(List<Orders> orders, List<ProjectType> projectTypes){
        this.orders = orders;
        this.projectTypes = projectTypes;
    }
}
