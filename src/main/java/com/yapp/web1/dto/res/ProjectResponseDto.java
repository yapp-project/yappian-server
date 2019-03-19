package com.yapp.web1.dto.res;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.ProjectType;
import com.yapp.web1.dto.req.ProjectRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 프로젝트 생성 후 ResponseDto
 * : 프로젝트 생성 후 바로 프로젝트 상세 정보를 보여주기 위함.
 * @author JiHye Kim
 * @author Dakyung Ko
 */

@Setter
@Getter
public class ProjectResponseDto {
    private Long ordersIdx; // 기수 idx
    private int orderNumber; // 기수 number
    private ProjectType projectType; // 프로젝트 타입
    private String projectName; // 프로젝트 이름
    private List<TaskListResponseDto> taskList; // 테스크 리스트

    @Builder
    public ProjectResponseDto(Project project, List<TaskListResponseDto> taskList){
        this.ordersIdx = project.getOrders().getIdx();
        this.orderNumber = project.getOrders().getNumber();
        this.projectType = project.getType();
        this.projectName = project.getName();
        this.taskList = taskList;
    }
}
