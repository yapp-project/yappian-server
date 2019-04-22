package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.UrlType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * 테스크 조회시 ResponseDto
 *
 * @author JiHye Kim
 * @author Dakyung Ko
 */
@Getter
public class TaskResponseDto {
    private String taskTitle; // 제목
    private UrlType urlType;// 상태
    private TaskJob taskJob; // 담당 직군
    private List<UserResponseDto> userList; // 담당자 유저 목록
    private LocalDate startDate; // 시작일
    private LocalDate endDate; // 종료일
    private String contents; // 상세 내용
    // 추후 파일 추가
    private List<CommentResponseDto> comments; // 댓글 쓴 User idx, 이름, 댓글 내용

    @Builder
    public TaskResponseDto(String taskTitle, UrlType urlType, TaskJob taskJob, LocalDate startDate, LocalDate endDate,
                           String contents, List<UserResponseDto> userList, List<CommentResponseDto> comments){
        this.taskTitle = taskTitle;
        this.urlType = urlType;
        this.taskJob = taskJob;
        this.userList = userList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
        this.comments = comments;
    }
}
