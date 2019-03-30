package com.yapp.web1.dto.req;

import com.yapp.web1.domain.File;
import com.yapp.web1.domain.VO.TaskJob;
import com.yapp.web1.domain.VO.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * 테스크 수정시 RequestDto
 *
 * @author JiHye Kim
 * @author Dakyung Ko
 */
@NoArgsConstructor
@Setter
@Getter
public class TaskUpdateRequestDto {
    @NotNull
    private Long taskIdx; // 테스크 idx
    @NotBlank(message = "테스크 제목을 입력하세요")
    @Size(min = 3)
    private String title; // 테스크 제목
    @NotNull
    private TaskStatus taskStatus; // 테스크 상태
    @NotNull
    private TaskJob taskJob; // 직군 선택
    @NotNull
    private List<Long> userList; // 프로젝트 참여자 userIdx List
    @NotNull
    private LocalDate startDate; // 테스크 기간 - 시작
    @NotNull
    private LocalDate endDate; // 테스크 기간 - 끝

    private String contents; // 테스크 설명
//    private List<File> fileList; // 테스크 첨부파일 List, 추후 수정

    @Builder
    public TaskUpdateRequestDto(Long taskIdx, String title, TaskStatus taskStatus, TaskJob taskJob,
                              List<Long> userList, LocalDate startDate, LocalDate endDate,
                                String contents, List<File> fileList){
        this.title = title;
        this.taskStatus = taskStatus;
        this.taskJob = taskJob;
        this.userList = userList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
//        this.fileList = fileList;
    }
}
