package com.yapp.web1.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 업로드 한 파일의 정보
 */
@Setter
@Getter
public class FileUploadResponseDto {

    private Long fileIdx;
    private String originName; // 사용자가 올린 파일의 원래 이름
    private String fileUrl; // 파일명 중복방지를 위해 생성된 s3에 올라가는 파일명

    @Builder
    public FileUploadResponseDto(Long fileIdx, String originName, String fileUrl){
        this.fileIdx = fileIdx;
        this.originName = originName;
        this.fileUrl = fileUrl;
    }
}
