package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.ProjectType;
import com.yapp.web1.domain.VO.TaskJob;
import lombok.Builder;
import lombok.Getter;

/**
 * 알림 리스트 Dto
 *
 * @author JiHye Kim
 * @author Dakyung Ko
 */
@Getter
public class NoticeListResponseDto {
    private int orderNumber; // 프로젝트 기수
    private int projectName; // 프로젝트 이름
    private ProjectType projectType; // 프로젝트 타입

    private Long taskIdx; // 테스크 idx
    private TaskJob taskJob; // 테스크 직군
    private String taskTitle; // 테스크 제목
    private Mark readCheck; // Task 읽음 여부

    @Builder
    public NoticeListResponseDto(int orderNumber, int projectName, ProjectType projectType,
                                 Long taskIdx, TaskJob taskJob, String taskTitle, Mark readCheck){
        this.orderNumber = orderNumber;
        this.projectName = projectName;
        this.projectType = projectType;
        this.taskIdx = taskIdx;
        this.taskJob = taskJob;
        this.taskTitle = taskTitle;
        this.readCheck = readCheck;
    }
}
