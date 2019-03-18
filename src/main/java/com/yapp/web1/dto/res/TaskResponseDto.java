package com.yapp.web1.dto.res;

import com.yapp.web1.domain.Comment;
import com.yapp.web1.domain.User;
import com.yapp.web1.domain.VO.TaskJob;
import com.yapp.web1.domain.VO.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

/**
 * 테스크 생성시 ResponseDto
 * @author JiHye Kim
 * @author Dakyung Ko
 */
@Getter
public class TaskResponseDto {
    private String taskTitle; // 제목
    private TaskStatus taskStatus;// 상태
    private TaskJob taskJob; // 담당 직군
    private LocalDate startDate; // 시작일
    private LocalDate endDate; // 종료일
    private String contents; // 상세 내용
    private List<User> userNameList; // 담당자 유저 이름
    private List<Comment> comments; // 댓글 쓴 사람 이름, 댓글 내용

    @Builder
    public TaskResponseDto(String taskTitle, TaskStatus taskStatus, TaskJob taskJob, LocalDate startDate, LocalDate endDate,
                           String contents, List<User> userNameList, List<Comment> comments){
        this.taskTitle = taskTitle;
        this.taskStatus = taskStatus;
        this.taskJob = taskJob;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
        this.userNameList = userNameList;
        this.comments = comments;
    }

}
