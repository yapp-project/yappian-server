package com.yapp.web1.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

@Component
@PropertySource("classpath:application.yml")
public class S3Util {

  //@Value("${aws.credentials.accessKey}")
    static private String accessKey="임시";

   // @Value("${aws.credentials.secretKey}")
    static private String secretKey="임시";

   //@Value("${amazonProperties.bucketName}")
    private String bucketName="yappian";

    private AmazonS3 connection;

    private String uploadPath = "Files";//s3 폴더명

    public S3Util(){
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        this.connection = new AmazonS3Client(credentials, clientConfig);
        connection.setEndpoint("s3.ap-northeast-2.amazonaws.com"); // 엔드포인트 (아시아 태평양 서울)
    }

    //파일 업로드
    public void fileUpload(String fileName, byte[] fileData) throws FileNotFoundException {
        String filePath = (fileName).replace(File.separatorChar, '/');//파일 경로를 분리해주는 메서드
        ObjectMetadata metaData = new ObjectMetadata();

        metaData.setContentLength(fileData.length);//파일 크기만큼 버퍼를 설정
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);

        connection.putObject(bucketName, filePath, byteArrayInputStream, metaData);
    }

    //파일 삭제
    public void fileDelete(String fileName){
        String getName = (fileName).replace(File.separatorChar, '/');
        connection.deleteObject(bucketName, getName);
    }

    //파일 URL
    public String getFileURL(String fileName){
        String getName = (uploadPath+fileName).replace(File.separatorChar,'/');
        return connection.generatePresignedUrl(new GeneratePresignedUrlRequest((bucketName), getName)).toString();//파일에 대해 접근할 수 있는 pre-signed URL리턴
    }
}
