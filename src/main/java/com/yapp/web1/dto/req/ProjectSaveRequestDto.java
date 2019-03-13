package com.yapp.web1.dto.req;

import com.yapp.web1.domain.VO.ProjectType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 프로젝트 생성시 RequestDto
 * @author JiHye Kim
 */

@NoArgsConstructor
@Setter
@Getter
public class ProjectSaveRequestDto {
    // validation 추가해야 함

    private Long ordersIdx; // 기수 idx
    private ProjectType projectType; // 프로젝트 타입
    private String projectName; // 프로젝트 이름
    private Long createUserIdx; // 프로젝트 생성자Idx

    @Builder
    public ProjectSaveRequestDto(Long ordersIdx, ProjectType projectType, String projectName, Long createUserIdx){
        this.ordersIdx = ordersIdx;
        this.projectType = projectType;
        this.projectName = projectName;
        this.createUserIdx = createUserIdx;
    }
}
