package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.ProjectType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 프로젝트 완료 페이지 Dto
 *
 * @author JiHye Kim
 * @author Dakyung Ko
 */
@Getter
public class FinishProjectResponseDto {

    private Long projectIdx; // idx
    private int ordersNumber; // 기수
    private ProjectType projectType; // 프로젝트 타입
    private String projectName; // 프로젝트 이름
    private String projectDescription; // 프로젝트 설명
    private String productURL; // 바로가기 URL
    private Mark releaseCheck; // 런칭 유무
    private List<FileUploadResponseDto> fileList; // 올린 파일의 정보 리스트

    @Builder
    public FinishProjectResponseDto(Long projectIdx, int ordersNumber, ProjectType projectType, String projectName,
                                String projectDescription, String productURL, Mark releaseCheck, List<FileUploadResponseDto> fileList){

        this.projectIdx = projectIdx;
        this.ordersNumber = ordersNumber;
        this.projectType = projectType;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.productURL = productURL;
        this.releaseCheck = releaseCheck;
        this.fileList = fileList;
    }
}
