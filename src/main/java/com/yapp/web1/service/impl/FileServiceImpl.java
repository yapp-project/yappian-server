package com.yapp.web1.service.impl;

import com.yapp.web1.domain.File;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.FileType;
import com.yapp.web1.dto.res.FileUploadResponseDto;
import com.yapp.web1.repository.FileRepository;
import com.yapp.web1.service.CommonService;
import com.yapp.web1.service.FileService;
import com.yapp.web1.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;
    @Autowired
    private CommonService commonService;
    @Autowired
    S3Service s3Service;

    String uploadPath = "Files";// s3 폴더명

    // 파일 경로명 월별 설정 메소드
    private static String calcPath(String uploadePath) {
        Calendar cal = Calendar.getInstance();

        String yearPath = java.io.File.separator + cal.get(Calendar.YEAR);

        String monthPath = yearPath + java.io.File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

        makeDir(uploadePath, yearPath, monthPath);

        return monthPath;
    }

    // 파일 경로명 월별 설정 메소드
    private static void makeDir(String uploadePath, String... paths) {
        if (new java.io.File(paths[paths.length - 1]).exists()) {
            return;
        }
        for (String path : paths) {
            java.io.File dirPath = new java.io.File(uploadePath + path);

            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        }
    }

    // 파일명 중복방지를 위해 파일명 변경하는 메소드
    private String createUrlName(MultipartFile multipartFiles) {

        UUID uid = UUID.randomUUID();//랜덤의 uid생성

        String saveName = "/" + uid.toString() + "_" + multipartFiles.getOriginalFilename(); //파일명중복방지를위해 파일명변경 : 파일명=/uid_원래파일명

        // \2019\05\07 같은 형태로 저장해준다.
        String savedPath = calcPath(uploadPath);

        String uploadedFileName = null;

        // 파일 구별자를 `/`로 설정(\->/) 이게 기존에 / 였어도 넘어오면서 \로 바뀐다.
        uploadedFileName = (savedPath) + saveName.replace(java.io.File.separatorChar, '/');

        return (uploadedFileName).replace(java.io.File.separatorChar, '/');

    }

    // get file list
    @Transactional(readOnly = true)
    @Override
    public List<File> getFiles(Long projectIdx){
        return fileRepository.findByProjectIdx(projectIdx);
    }

    // filesResDto
    @Override
    public List<FileUploadResponseDto> getFileList(Long projectIdx){
        List<File> files = this.getFiles(projectIdx);
        List<FileUploadResponseDto> fileList = new ArrayList<>();

        for(File file : files){
            FileUploadResponseDto dto = FileUploadResponseDto.builder()
                    .fileIdx(file.getIdx())
                    .type(file.getType())
                    .originName(file.getName())
                    .fileUrl(file.getFileURL())
                    .build();
            fileList.add(dto);
        }

        return fileList;
    }

    // fileUpload
    @Override
    public List<FileUploadResponseDto> fileUpload(MultipartFile[] multipartFiles, Long projectIdx) {

        Project project = commonService.findById(projectIdx);

        int sw = 1;
        FileType type = null;
        List<FileUploadResponseDto> fileUploadResponseDtos = new ArrayList<>();

        for (MultipartFile files : multipartFiles) {
            if (sw == 1) {
                type = FileType.IMAGE;
            } else if (sw == -1) {
                type = FileType.PDF;
            }

            String originName = files.getOriginalFilename();

            String createdUrl = createUrlName(files);

            s3Service.upload(files, uploadPath + createdUrl); // 실제 파일 업로드

            String downUrl = "https://s3.ap-northeast-2.amazonaws.com/yappian/"+uploadPath+createdUrl;
            downUrl = (downUrl).replace(java.io.File.separatorChar, '/');

            File file = File.builder()
                    .name(originName)
                    .fileURL(downUrl)
                    .type(type)
                    .project(project)
                    .build();

            fileRepository.save(file);

            fileUploadResponseDtos.add(new FileUploadResponseDto(file.getIdx(),file.getType(), originName,downUrl));

            sw *= -1;
        }

        return fileUploadResponseDtos;
    }

    // deleteAllFile
    @Override
    public void deleteAllFile(Long projectIdx) {
        List<File> fileList = fileRepository.findByProjectIdx(projectIdx);
        for (File file : fileList) {
            s3Service.fileDelete(uploadPath + file.getName());
        }
        fileRepository.deleteByProjectIdx(projectIdx);
    }
}
