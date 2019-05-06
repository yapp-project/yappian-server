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

@Service
public class S3Service {

    private static String bucket;

    @Value("${amazonProperties.bucket}")
    public void setBucket(String bucket){this.bucket = bucket;}

    @Autowired
    private AmazonS3Client amazonS3Client;

    public void upload(MultipartFile multipartFiles, String fileName) {

        String filePath = (fileName).replace(java.io.File.separatorChar, '/');//파일 경로를 분리해주는 메서드

        if(StringUtils.isEmpty(multipartFiles.getOriginalFilename())){
            System.out.println("file 없음");
        }
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filePath, multipartFiles.getInputStream(), new ObjectMetadata());
            amazonS3Client.putObject(putObjectRequest);
            IOUtils.closeQuietly(multipartFiles.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 실제론 구현할 필요 없음 일단 확인하기 위함임.
    public ResponseEntity<byte[]> download(String key) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);
        String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public List<S3ObjectSummary> list() {
        ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));
        List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();
        return s3ObjectSummaries;
    }

    //파일 삭제
    public void fileDelete(String fileName){
        String name = (fileName).replace(File.separatorChar, '/');
        amazonS3Client.deleteObject(bucket, name);
    }

}
