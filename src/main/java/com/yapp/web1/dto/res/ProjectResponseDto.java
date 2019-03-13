package com.yapp.web1.dto.res;

import com.yapp.web1.dto.req.ProjectRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 프로젝트 생성 후 ResponseDto
 * : 프로젝트 생성 후 바로 프로젝트 상세 정보를 보여주기 위함.
 * @author JiHye Kim
 * @author Dakyung Ko
 */

@Getter
public class ProjectResponseDto {
    private ProjectRequestDto project;// 프로젝트 생성시 데이터들의 객체
    private List<TaskListResponseDto> taskList; // 테스크 리스트

    @Builder
    public ProjectResponseDto(ProjectRequestDto project, List<TaskListResponseDto> taskList){
        this.project = project;
        this.taskList = taskList;
    }
}
