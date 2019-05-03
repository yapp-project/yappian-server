package com.yapp.web1.service.impl;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.yapp.web1.domain.File;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.FileType;
import com.yapp.web1.repository.FileRepository;
import com.yapp.web1.service.CommonService;
import com.yapp.web1.service.FileService;
import com.yapp.web1.service.S3Service;
import com.yapp.web1.util.S3Util;
import com.yapp.web1.util.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    @Autowired S3Util s3Util;
    @Autowired FileRepository fileRepository;
    @Autowired private CommonService commonService;
    @Autowired S3Service s3Service;


    private static String calcPath(String uploadePath){
        Calendar cal = Calendar.getInstance();

        String yearPath = java.io.File.separator+cal.get(Calendar.YEAR);

        String monthPath = yearPath + java.io.File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);

        makeDir(uploadePath, yearPath, monthPath);

        return monthPath;
    }

    private static void makeDir(String uploadePath, String... paths){
        if(new java.io.File(paths[paths.length-1]).exists()){
            return;
        }
        for(String path : paths){
            java.io.File dirPath = new java.io.File(uploadePath+path);

            if(!dirPath.exists()){
                dirPath.mkdir();
            }
        }
    }

    // createdUrlName
    private String createUrlName(MultipartFile multipartFiles){

        String uploadPath = "Files";// s3 폴더명
        UUID uid = UUID.randomUUID();//랜덤의 uid생성

        String saveName = "/"+uid.toString()+"_"+multipartFiles.getOriginalFilename(); //파일명중복방지를위해 파일명변경 : 파일명=/uid_원래파일명

        String savedPath = calcPath(uploadPath);

        String uploadedFileName = null;

        uploadedFileName = (savedPath)+saveName.replace(java.io.File.separatorChar,'/');

        // TODO 폴더 안에 후 작업해야함.
        s3.fileUpload(uploadPath+uploadedFileName, byteData);

        return (uploadedFileName).replace(java.io.File.separatorChar, '/');

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

        // 파일 이름은 업로드할때는 랜덤 문자 붙이고 이걸 따로 저장한다.
        // 디비에는 id, url : 랜덤문자+파일명일것이다. 그리고 originName : 원래파일명
        // 클라이언트에게 해당 플젝트의 List<파일>을 준다.

        // 파일 이름 및 파일 이름 변경 작업
        // 파일 업로드하기
        // 업로드 한 파일의 이름 Get
        // save

        int sw = 1;
        FileType type = null;
        List<PutObjectResult> putObjectResults = new ArrayList<>();

        for(MultipartFile files : multipartFiles){
            if(sw==1){
                type = FileType.IMAGE;
            }else if(sw==-1){
                type = FileType.PDF;
            }

            String createdUrl = createUrlName(files);

            s3Service.upload(files,createdUrl);

            File file = File.builder()
                    .project(project)
                    .name(getName(files))
                    .type(type)
                    .build();

            setUrl(file);
            fileRepository.save(file);
            sw*=-1;
        }

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
