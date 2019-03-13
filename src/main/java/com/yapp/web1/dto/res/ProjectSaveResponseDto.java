package com.yapp.web1.dto.res;

import com.yapp.web1.dto.req.ProjectSaveRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 프로젝트 생성 후 ResponseDto
 * 프로젝트 생성 후 바로 프로젝트 상세 정보를 보여주기 위함.
 * @author JiHye Kim
 * @author Dakyung Ko
 */

@Getter
public class ProjectSaveResponseDto {
    ProjectSaveRequestDto project;// 프로젝트 생성시 데이터들의 객체
    List<TaskListResponseDto> taskListResponseDto; // 테스크 리스트

    @Builder
    public ProjectSaveResponseDto(ProjectSaveRequestDto project, List<TaskListResponseDto> taskListResponseDto){
        this.project = project;
        this.taskListResponseDto = taskListResponseDto;
    }
}
