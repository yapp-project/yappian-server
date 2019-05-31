package com.yapp.web1.service;

import com.yapp.web1.domain.File;
import com.yapp.web1.dto.res.FileUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    /**
     * 해당 프로젝트의 file 목록
     */
    List<File> getFiles(Long projectIdx);

    /**
     * 해당 프로젝트의 FileResDto 목록
     */
    List<FileUploadResponseDto> getFileList(Long projectIdx);

    /**
     * 해당 프로젝트의 모든 file 삭제
     */
    void deleteAllFile(Long projectIdx);

    /**
     * 파일 업로드 후, 업로드 한 파일의 정보 목록
     *
     * @param multipartFiles
     * @param projectIdx
     * @return
     */
    List<FileUploadResponseDto> fileUpload(MultipartFile[] multipartFiles, Long projectIdx) throws IOException;
}
