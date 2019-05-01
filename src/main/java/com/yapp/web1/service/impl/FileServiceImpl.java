package com.yapp.web1.service.impl;

import com.yapp.web1.domain.File;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.FileType;
import com.yapp.web1.repository.FileRepository;
import com.yapp.web1.service.CommonService;
import com.yapp.web1.service.FileService;
import com.yapp.web1.util.S3Util;
import com.yapp.web1.util.UploadFileUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    @Autowired
    S3Util s3Util;
    private FileRepository fileRepository;
    private CommonService commonService;

    private String uploadPath = "Files";// s3 폴더명

    // getName
    private String getName(MultipartFile multipartFiles){
        try{
           return UploadFileUtils.uploadFile(uploadPath, multipartFiles.getOriginalFilename(), multipartFiles.getBytes());
        }catch (Exception e){
            return "파일 업로드 에러";
        }
    }

    // setUrl
    private void setUrl(File file){
        try{
            file.setFileURL(new URL(s3Util.getFileURL(file.getName())).toString());
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    // fileUpload
    @Override
    public void fileUpload(MultipartFile[]multipartFiles, Long projectIdx){

        Project project = commonService.findById(projectIdx);

        int sw = 1;
        FileType type = null;

        for(MultipartFile files : multipartFiles){
            if(sw==1){
                type = FileType.IMAGE;
            }else if(sw==-1){
                type = FileType.PDF;
            }

            File file = File.builder()
                    .project(project)
                    .name(getName(files))
                    .type(type)
                    .build();

            setUrl(file);
            fileRepository.save(file);
            sw*=-1;
        }
        /*
        // image
        File setImage = File.builder()
                .project(project)
                .name(getName(multipartFiles[0]))
                .type(FileType.IMAGE)
                .build();

        setUrl(setImage);
        fileRepository.save(setImage);

        // pdf
        File setPdf = File.builder()
                .project(project)
                .name(getName(multipartFiles[1]))
                .type(FileType.PDF)
                .build();

        setUrl(setPdf);
        fileRepository.save(setPdf);
        */
    }

    // deleteAllFile
    @Override
    public void deleteAllFile(Long projectIdx){
        List<File> fileList = fileRepository.findByProjectIdx(projectIdx);
        for(File file : fileList)
            s3Util.fileDelete(uploadPath+file.getName());
        fileRepository.deleteByProjectIdx(projectIdx);
    }

}
