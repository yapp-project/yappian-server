package com.yapp.web1.dto.res;


import com.yapp.web1.domain.VO.ProjectType;
import com.yapp.web1.domain.VO.TaskJob;
import lombok.Builder;
import lombok.Getter;

/**
 * 알림 리스트 Dto
 * @author JiHye Kim
 */
@Getter
public class NoticeListResponseDto {
    int orderNumber; // 프로젝트 기수
    int projectName; // 프로젝트 이름
    ProjectType projectType; // 프로젝트 타입
    TaskJob taskJob; // 테스크 직군
    String taskTitle; // 테스크 제목

    //추후 읽음 여부

    @Builder
    public NoticeListResponseDto(int orderNumber, int projectName, ProjectType projectType,
                                 TaskJob taskJob, String taskTitle){
        this.orderNumber = orderNumber;
        this.projectName = projectName;
        this.projectType = projectType;
        this.taskJob = taskJob;
        this.taskTitle = taskTitle;
    }

}
