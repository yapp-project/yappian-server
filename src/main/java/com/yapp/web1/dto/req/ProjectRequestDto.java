package com.yapp.web1.dto.req;

import com.yapp.web1.domain.VO.ProjectType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 프로젝트 생성/수정 시 RequestDto
 *
 * @author JiHye Kim
 * @author Dakyung Ko
 */

@NoArgsConstructor
@Setter
@Getter
@ApiModel(description = "Project 생성 또는 수정에 사용하는 모델")
public class ProjectRequestDto {

    @NotNull(message = "기수를 입력하세요.")
    @ApiModelProperty(notes = "기수 idx")
    private Long ordersIdx; // 기수 idx

    @NotNull(message = "프로젝트 타입을 입력하세요")
    @ApiModelProperty(notes = "프로젝트 타입 (WEB,ANDROID,IOS) (순서대로 숫자 0,1,2도 가능)")
    private ProjectType projectType; // 프로젝트 타입

    @NotBlank(message = "프로젝트 이름을 입력하세요")
    @ApiModelProperty(notes = "최대 글자수 9")
    @Size(max=9)
    private String projectName; // 프로젝트 이름

    @NotNull(message = "비밀번호를 입력하세요")
    @ApiModelProperty(notes = "4자리")
    @Size(min = 4, max = 4)
    private String password; // 비밀번호

    @Builder
    public ProjectRequestDto(Long ordersIdx,String projectName, ProjectType projectType, String password){
        this.ordersIdx = ordersIdx;
        this.projectType = projectType;
        this.projectName = projectName;
        this.password = password;
    }
}
