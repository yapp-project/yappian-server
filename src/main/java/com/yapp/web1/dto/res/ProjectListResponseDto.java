package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.ProjectType;
import lombok.Builder;
import lombok.Getter;

/**
 * 프로젝트 리스트 Dto
 *
 * @author JiHye Kim
 */
@Getter
public class ProjectListResponseDto {

    private Long projectIdx; // 프로젝트 idx
    private ProjectType type; //프로젝트 타입
    private String name; // 프로젝트 이름
    private Mark releaseCheck; // 프로젝트 런칭 여부
    private String imgUrl; // 대표 이미지 url

    @Builder
    public ProjectListResponseDto(Long projectIdx, ProjectType type, String name, Mark releaseCheck, String imgUrl){
       this.projectIdx = projectIdx;
       this.type = type;
       this.name = name;
       this.releaseCheck = releaseCheck;
       this.imgUrl = imgUrl;
    }

}
