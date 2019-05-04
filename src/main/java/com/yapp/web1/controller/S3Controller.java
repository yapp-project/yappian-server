package com.yapp.web1.controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.yapp.web1.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    /*
    @PostMapping("/upload")
    public List<PutObjectResult> upload(@RequestParam("file") MultipartFile[] multipartFiles) {
        return s3Service.upload(multipartFiles);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String key) throws IOException {
        return s3Service.download(key);
    }

    @GetMapping("/fileList")
    public List<S3ObjectSummary> list() throws IOException {
        return s3Service.list();
    }
    */
}
