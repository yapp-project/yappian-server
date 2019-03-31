package com.yapp.web1.dto.req;

import com.yapp.web1.domain.File;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.Task;
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
 * 테스크 생성시 RequestDto
 *
 * @author JiHye Kim
 * @author Dakyung Ko
 */
@NoArgsConstructor
@Setter
@Getter
public class TaskRequestDto {
    private Long projectIdx; // 관계된 프로젝트 idx

    @NotBlank(message = "테스크 제목을 입력하세요")
    @Size(max=17)
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
//    List<File> fileList; // 테스크 첨부파일 List, 추후 파일 수정

    @Builder
    public TaskRequestDto(Long projectIdx, String title, TaskStatus taskStatus, TaskJob taskJob,
                          List<Long> userList, LocalDate startDate, LocalDate endDate, String contents, List<File> fileList){
        this.projectIdx = projectIdx;
        this.title = title;
        this.taskStatus = taskStatus;
        this.taskJob = taskJob;
        this.userList = userList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
//        this.fileList = fileList;
    }

    public Task toEntity(Project project){
        return Task.builder().title(this.title)
                .status(this.taskStatus)
                .job(this.taskJob)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .contents(this.contents)
                .project(project)
                .build();
    }
}
