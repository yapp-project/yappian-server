package com.yapp.web1.service;

import com.yapp.web1.domain.File;
import com.yapp.web1.dto.res.FileUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * FileService Interface
 *
 * @author Jihye Kim
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.1
 */
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
     * @param file
     * @param projectIdx
     * @param accountIdx 권한 체크용 유저 idx
     * @return
     */
    FileUploadResponseDto fileUpload(MultipartFile file, Long projectIdx, Long accountIdx) throws IOException;

    /**
     * File 객체를 FileUploadResponseDto 객체로 변환
     *
     * @param file
     * @return
     */
    FileUploadResponseDto convertToDto(File file);
}
