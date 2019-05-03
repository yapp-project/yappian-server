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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class S3Service {

    private static String bucket;

    @Value("${amazonProperties.bucket}")
    public void setBucket(String bucket){this.bucket = bucket;}

    @Autowired
    private AmazonS3Client amazonS3Client;

    private PutObjectResult upload(InputStream inputStream, String fileName) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, inputStream, new ObjectMetadata());
        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
        IOUtils.closeQuietly(inputStream);
        return putObjectResult;
    }

    public List<PutObjectResult> upload(MultipartFile multipartFiles, String createdUrl) {

        List<PutObjectResult> putObjectResults = new ArrayList<>();

        if(StringUtils.isEmpty(multipartFiles.getOriginalFilename())){
            System.out.println("file 이름 없음");
            return null;
        }
        try {
            putObjectResults.add(upload(multipartFiles.getInputStream(), createdUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return putObjectResults;
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
    /*

    //파일이름(파일경로+uid+파일명)생성 후 S3Util이용해 파일 업로드하는 메서드
    public static String uploadFile(String uploadPath, String originalName, byte[] byteData) throws Exception{
        S3Util s3 = new S3Util();

        UUID uid = UUID.randomUUID();//랜덤의 uid생성

        String saveName = "/"+uid.toString()+"_"+originalName; //파일명중복방지를위해 파일명변경 : 파일명=/uid_원래파일명

        String savedPath = calcPath(uploadPath);

        String uploadedFileName = null;

        uploadedFileName = (savedPath)+saveName.replace(File.separatorChar,'/');

        s3.fileUpload(uploadPath+uploadedFileName, byteData);

        return (uploadedFileName).replace(File.separatorChar, '/');
    }

    private static String calcPath(String uploadePath){
        Calendar cal = Calendar.getInstance();

        String yearPath = File.separator+cal.get(Calendar.YEAR);

        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);

        makeDir(uploadePath, yearPath, monthPath);

        return monthPath;
    }

    private static void makeDir(String uploadePath, String... paths){
        if(new File(paths[paths.length-1]).exists()){
            return;
        }
        for(String path : paths){
            File dirPath = new File(uploadePath+path);

            if(!dirPath.exists()){
                dirPath.mkdir();
            }
        }
    }
     */
}
