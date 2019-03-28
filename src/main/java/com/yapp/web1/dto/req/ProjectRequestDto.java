package com.yapp.web1.dto.req;

import com.yapp.web1.domain.VO.ProjectType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 프로젝트 생성/수정 시 RequestDto
 * @author JiHye Kim
 * @author Dakyung Ko
 */

@NoArgsConstructor
@Setter
@Getter
public class ProjectRequestDto {
    @NotBlank(message = "기수를 입력하세요.")
    private Long ordersIdx; // 기수 idx

//    private int orderNumber; // 기수 number

    @NotBlank(message = "프로젝트 타입을 입력하세요")
    private ProjectType projectType; // 프로젝트 타입

    @NotBlank(message = "프로젝트 이름을 입력하세요")
    @Size(min = 3)
    private String projectName; // 프로젝트 이름

    @Builder
    public ProjectRequestDto(Long ordersIdx, ProjectType projectType, String projectName){
        this.ordersIdx = ordersIdx;
        this.projectType = projectType;
        this.projectName = projectName;
    }
}
