package com.yapp.web1.dto.res;

import com.yapp.web1.domain.Project;
import com.yapp.web1.dto.req.ProjectSaveRequestDto;
import lombok.Builder;
import lombok.Getter;

/**
 * 프로젝트 생성 후 ResponseDto
 * 프로젝트 생성 후 바로 프로젝트 상세 페이지로 가기 위함.
 * @author JiHye Kim
 */

@Getter
public class ProjectSaveResponseDto {
    Project project;// 프로젝트 생성시 데이터들의 객체
    TaskListResponseDto taskListResponseDto; // 테스크 리스트

    @Builder
    public ProjectSaveResponseDto(Project project, TaskListResponseDto taskListResponseDto){
        this.project = project;
        taskListResponseDto = taskListResponseDto;
    }
}
