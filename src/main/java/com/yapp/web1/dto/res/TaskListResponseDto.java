package com.yapp.web1.dto.res;

import com.yapp.web1.domain.Task;
import com.yapp.web1.domain.User;
import com.yapp.web1.domain.VO.TaskJob;
import com.yapp.web1.domain.VO.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * 테스크 리스트 ResponseDto
 * @author JiHye Kim
 */
@Getter
public class TaskListResponseDto {

    private Long taskIdx; // 테스크 idx
    private String taskTitle; // 테스크 제목
    private TaskJob taskJob; // 테스크 직군
    private Set<User> userList; // 테스크 담당자
    private LocalDate startDate; // 테스크 기간 - 시작
    private LocalDate endDate; // 테스크 기간 - 끝
    private TaskStatus taskStatus; // 테스크 진행상태

    @Builder
    public TaskListResponseDto(Task task){
        this.taskIdx = task.getIdx();
        this.taskTitle = task.getTitle();
        this.taskJob = task.getJob();
        this.userList = task.getManagers();
        this.startDate = task.getStartDate();
        this.endDate = task.getEndDate();
        this.taskStatus = task.getStatus();
    }
}
