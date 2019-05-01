package com.yapp.web1.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 해당 프로젝트의 모든 file 삭제
     */
    void deleteAllFile(Long projectIdx);

    void fileUpload(MultipartFile[] multipartFiles, Long projectIdx);
}
