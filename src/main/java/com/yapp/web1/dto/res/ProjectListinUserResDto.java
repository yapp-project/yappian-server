package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.ProjectType;
import lombok.Builder;
import lombok.Getter;

/**
 * 내가 조인된 프로젝트 목록 리스트
 *
 * @author JiHye Kim
 */

@Getter
public class ProjectListinUserResDto implements Comparable<ProjectListinUserResDto>{

    private Long idx;
    private int orderNumber; // 기수
    private ProjectType projectType; //프로젝트 타입
    private String projectName; // 프로젝트 이름

    @Builder
    public ProjectListinUserResDto(Long idx, int orderNumber, ProjectType projectType, String projectName){
        this.idx = idx;
        this.orderNumber = orderNumber;
        this.projectType = projectType;
        this.projectName = projectName;
    }

    @Override
    public int compareTo(ProjectListinUserResDto p){
        return p.orderNumber - this.orderNumber;
    }
}
