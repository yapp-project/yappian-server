package com.yapp.web1.dto.res;

import com.yapp.web1.domain.Url;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.UrlType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * 테스크 리스트 ResponseDto
 *
 * @author JiHye Kim
 */
@Getter
public class TaskListResponseDto {

    private Long taskIdx; // 테스크 idx
    private String taskTitle; // 테스크 제목
    private TaskJob taskJob; // 테스크 직군
    private List<UserResponseDto> userList; // 테스크 담당자
    private LocalDate startDate; // 테스크 기간 - 시작
    private LocalDate endDate; // 테스크 기간 - 끝
    private UrlType urlType; // 테스크 진행상태
    private Mark readCheck; // 테스크 읽음여부 체크

    @Builder
    public TaskListResponseDto(Url url, List<UserResponseDto> userList) {
        this.taskIdx = url.getIdx();
        this.taskTitle = url.getTitle();
        this.taskJob = url.getJob();
        this.userList = userList;
        this.startDate = url.getStartDate();
        this.endDate = url.getEndDate();
        this.urlType = url.getType();
        this.readCheck = Mark.N; // 추후 수정해야함.
    }
}
