package com.yapp.web1.dto.res;

import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import lombok.Builder;
import lombok.Getter;

/**
 * 프로젝트 완료 페이지 작성 Dto
 * @author Dakyung Ko
 */
@Getter
public class FinishProjectResponseDto {
    private ProjectRequestDto project;
    private FinishProjectRequestDto finishProject;

    @Builder
    public FinishProjectResponseDto(ProjectRequestDto projectDto, FinishProjectRequestDto finishDto){
        this.project = projectDto;
        this.finishProject = finishDto;
    }
}
