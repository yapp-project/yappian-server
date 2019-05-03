package com.yapp.web1.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    private static String accessKey;
    private static String secretKey;
    private static String region;

    @Value("${amazonProperties.accessKey}")
    public void setAccessKey(String accessKey){this.accessKey = accessKey;}

    @Value("${amazonProperties.secretKey}")
    public void setSecretKey(String secretKey){this.secretKey = secretKey;}

    @Value("${amazonProperties.region}")
    public void setRegion(String region){this.region = region;}

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
        AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
        amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));
        return amazonS3Client;
    }
}
