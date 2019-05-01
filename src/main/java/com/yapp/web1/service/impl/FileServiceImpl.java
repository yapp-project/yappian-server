package com.yapp.web1.service.impl;

import com.yapp.web1.repository.FileRepository;
import com.yapp.web1.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;

    // deleteAllFile
    @Override
    public void deleteAllFile(Long projectIdx){fileRepository.deleteByProjectIdx(projectIdx);}

    @Override
    public void fileUpload(MultipartFile[]multipartFiles, Long projectIdx){

    }
}
