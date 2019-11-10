package com.yapp.web1.service.impl;

import com.yapp.web1.domain.File;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.FileType;
import com.yapp.web1.dto.res.FileUploadResponseDto;
import com.yapp.web1.repository.FileRepository;
import com.yapp.web1.service.CommonService;
import com.yapp.web1.service.FileService;
import com.yapp.web1.service.ProjectService;
import com.yapp.web1.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;
    private CommonService commonService;
    private S3Service s3Service;
    private ProjectService projectService;

    private final String uploadPath = "Files";// s3 폴더명
    private final byte[] JPG_MAGIC = new byte[] {(byte)0xFF, (byte)0xD8, (byte)0xFF}; // jpeg, jpg
    private final byte[] PNG_MAGIC = new byte[] {(byte)0x89, (byte)0x50, (byte)0x4E};
    private final byte[] PDF_MAGIC = new byte[] {(byte)0x25, (byte)0x50, (byte)0x44};

    // 파일 경로명 월별 설정 메소드
    private static String calcPath(String uploadPath) {
        Calendar cal = Calendar.getInstance();

        String yearPath = java.io.File.separator + cal.get(Calendar.YEAR);

        String monthPath = yearPath + java.io.File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

        makeDir(uploadPath, yearPath, monthPath);

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
    public FileUploadResponseDto fileUpload(MultipartFile multipartFile, Long projectIdx, Long accountIdx) throws IOException{

        Project project = commonService.findById(projectIdx);

        commonService.checkAccountPermission(projectService.getAccountListInProject(projectIdx), accountIdx);

        FileType type = this.fileTypeCheck(multipartFile);

        String originalFilename = multipartFile.getOriginalFilename();
        String createdUrl = createUrlName(multipartFile);

        s3Service.upload(multipartFile, uploadPath + createdUrl); // 실제 파일 업로드

        String downUrl = "https://s3.ap-northeast-2.amazonaws.com/yappian/" + uploadPath + createdUrl;
        downUrl = (downUrl).replace(java.io.File.separatorChar, '/');

        File file = File.builder()
                .name(originalFilename)
                .fileURL(downUrl)
                .type(type)
                .project(project)
                .build();

        return this.convertToDto(fileRepository.save(file));
    }

    @Override
    public FileUploadResponseDto convertToDto(File file) {
        return FileUploadResponseDto.builder()
                    .fileIdx(file.getIdx())
                    .originName(file.getName())
                    .type(file.getType())
                    .fileUrl(file.getFileURL())
                    .build();
    }

    private FileType fileTypeCheck(MultipartFile file) throws IOException{
        if (file != null && file.getSize() > 0 && file.getOriginalFilename() != null) {
            try (InputStream in = file.getInputStream()) {
                byte[] magic = new byte[3];
                int count = in.read(magic);
                if (count < 3) throw new IOException();

                if (Arrays.equals(magic, JPG_MAGIC) || Arrays.equals(magic, PNG_MAGIC)) {
                    return FileType.IMAGE;
                } else if (Arrays.equals(magic, PDF_MAGIC)) {
                    return FileType.PDF;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new IOException();
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
