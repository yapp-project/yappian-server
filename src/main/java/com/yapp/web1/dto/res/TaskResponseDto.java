package com.yapp.web1.dto.res;

import com.yapp.web1.domain.Comment;
import com.yapp.web1.domain.User;
import com.yapp.web1.domain.VO.TaskJob;
import com.yapp.web1.domain.VO.TaskStatus;
import lombok.Builder;

import java.util.List;

/**
 * 테스크 생성시 ResponseDto
 * @author JiHye Kim
 */
public class TaskResponseDto {
    TaskStatus taskStatus;// 상태
    TaskJob taskJob; // 직군
    List<User> userNameList; // 담당자 유저 이름
    List<Comment> comments; // 댓글 쓴 사람 이름, 댓글 내용

    @Builder
    public TaskResponseDto(TaskStatus taskStatus, TaskJob taskJob, List<User> userNameList, List<Comment> comments){
        this.taskStatus = taskStatus;
        this.taskJob = taskJob;
        this.userNameList = userNameList;
        this.comments = comments;
    }

}
