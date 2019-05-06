package com.yapp.web1.dto.res;

import com.yapp.web1.domain.VO.FileType;
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
    private FileType type; // 파일 타입

    @Builder
    public FileUploadResponseDto(Long fileIdx, FileType type, String originName, String fileUrl){
        this.fileIdx = fileIdx;
        this.type = type;
        this.originName = originName;
        this.fileUrl = fileUrl;
    }
}
