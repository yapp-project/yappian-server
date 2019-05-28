package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.ProjectType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 프로젝트 정보
 *
 * @author JiHye Kim
 */
@Getter
public class ProjectsResDto {

    private Long idx;
    private String name;
    private String password;
    private ProjectType type;
    private Long ordersIdx; // 기수 idx
    private int orderNumber; // 기수 번호
    private Long createUserIdx; // 프로젝트 생성한 사람의 idx
    private Mark finalCheck; // 프로젝트 완료 여부
    private Mark releaseCheck; // 프로젝트 런칭 여부
    private String productURL; // 프로젝트 대표 URL
    private String description; // 프로젝트 상세 설명
    private List<AccountResponseDto> accountList; // User 리스트
    private List<UrlResponseDto>  urlList; // Url 리스트
    private List<FileUploadResponseDto> fileList; // 파일 리스트

    @Builder
    public ProjectsResDto(Long idx, String name, String password, ProjectType type,
                          Long ordersIdx, int orderNumber, Long createUserIdx,
                          Mark finalCheck, Mark releaseCheck, String productURL,
                          String description, List<AccountResponseDto> accountList,
                          List<UrlResponseDto> urlList, List<FileUploadResponseDto> fileList){

        this.idx = idx;
        this.name = name;
        this.password = password;
        this.type = type;
        this.ordersIdx = ordersIdx;
        this.orderNumber = orderNumber;
        this.createUserIdx = createUserIdx;
        this.finalCheck = finalCheck;
        this.releaseCheck = releaseCheck;
        this.productURL = productURL;
        this.description = description;
        this.accountList = accountList;
        this.urlList = urlList;
        this.fileList = fileList;
    }

}
