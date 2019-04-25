package com.yapp.web1.dto.res;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.Url;
import com.yapp.web1.domain.VO.ProjectType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 프로젝트 생성 후 ResponseDto
 * 프로젝트 생성 후 바로 프로젝트 상세 정보를 보여주기 위함.
 *
 * @author JiHye Kim
 * @author Dakyung Ko
 */
@Getter
public class ProjectResponseDto {

    private int orderNumber; // 기수
    private ProjectType projectType; // 프로젝트 타입
    private String projectName; // 프로젝트 이름
    private List<Url> urlList; // Url 리스트

    @Builder
    public ProjectResponseDto(Project project, List<Url> urlList){
        this.orderNumber = project.getOrders().getNumber();
        this.projectType = project.getType();
        this.projectName = project.getName();
        this.urlList = urlList;
    }
}
