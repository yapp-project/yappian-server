package com.yapp.web1.dto.req;

import com.yapp.web1.domain.Comment;
import com.yapp.web1.domain.File;
import com.yapp.web1.domain.User;
import com.yapp.web1.domain.VO.TaskJob;
import com.yapp.web1.domain.VO.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * 테스크 수정시 RequestDto
 * @author JiHye Kim
 */

@NoArgsConstructor
@Setter
@Getter
public class TaskUpdateRequestDto {
    String title; // 테스크 제목
    TaskStatus taskStatus; // 테스크 상태
    TaskJob taskJob; // 직군 선택
    List<User> userList; // 프로젝트 참여자이름 List
    LocalDate startDate; // 테스크 기간 - 시작
    LocalDate endDate; // 테스크 기간 - 끝
    String contents; // 테스크 설명
    List<File> fileList; // 테스크 첨부파일 List
    List<Comment> comments; // 댓글

    @Builder
    public TaskUpdateRequestDto(String title, TaskStatus taskStatus, TaskJob taskJob,
                              List<User> userList, LocalDate startDate, LocalDate endDate,
                                String contents, List<File> fileList, List<Comment> comments){
        this.title = title;
        this.taskStatus = taskStatus;
        this.taskJob = taskJob;
        this.userList = userList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
        this.fileList = fileList;
        this.comments = comments;
    }
}
