package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.TaskJob;
import com.yapp.web1.domain.VO.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * 테스크 리스트 ResponseDto
 * @author JiHye Kim
 */
@Getter
public class TaskListResponseDto {

    private Long taskIdx; // 테스크 idx
    private String taskTitle; // 테스크 제목
    private TaskJob taskJob; // 테스크 직군
    private List<String> userList; // 테스크 담당자
    private LocalDate startDate; // 테스크 기간 - 시작
    private LocalDate endDate; // 테스크 기간 - 끝
    private TaskStatus taskStatus; // 테스크 진행상태

    @Builder
    public TaskListResponseDto(Long taskIdx, String taskTitle, TaskJob taskJob, List<String> userList,
                               LocalDate startDate, LocalDate endDate, TaskStatus taskStatus){
        this.taskIdx = taskIdx;
        this.taskTitle = taskTitle;
        this.taskJob = taskJob;
        this.userList = userList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskStatus = taskStatus;
    }
}
