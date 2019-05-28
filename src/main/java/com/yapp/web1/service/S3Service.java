package com.yapp.web1.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * S3 Service
 *
 * @author Jihye Kim
 */
@Service
public class S3Service {

    private static String bucket;

    @Value("${amazonProperties.bucket}")
    public void setBucket(String bucket){this.bucket = bucket;}

    @Autowired
    private AmazonS3Client amazonS3Client;

    public void upload(MultipartFile multipartFiles, String fileName) {

        String filePath = (fileName).replace(java.io.File.separatorChar, '/');//파일 경로를 분리해주는 메서드

        String fileExtension = filePath.substring(filePath.lastIndexOf(".")+1, filePath.length());
        String type="jpeg";

        if(StringUtils.isEmpty(multipartFiles.getOriginalFilename())){
            System.out.println("file 없음");
        }
        try {
            ObjectMetadata metadata = new ObjectMetadata();

            // image without jpg
            if(!fileExtension.toLowerCase().equals(type) && !fileExtension.toLowerCase().equals("pdf")){
                type = fileExtension.toLowerCase();
                metadata.setContentType("image/"+type);
            }

            // jpg
            else if(!fileExtension.toLowerCase().equals("pdf")){
                metadata.setContentType("image/"+type);
            }

            // pdf
            else{
                type = fileExtension.toLowerCase();
                metadata.setContentType("application/"+type);
            }
            metadata.setContentDisposition("inline");
            metadata.setContentEncoding("UTF-8");

            // upload
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filePath, multipartFiles.getInputStream(), metadata);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3Client.putObject(putObjectRequest);
            IOUtils.closeQuietly(multipartFiles.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //파일 삭제
    public void fileDelete(String fileName){
        String name = (fileName).replace(File.separatorChar, '/');
        amazonS3Client.deleteObject(bucket, name);
    }

}
