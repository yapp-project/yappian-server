package com.yapp.web1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class HealthCheckController {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

    @GetMapping("/_hcheck")
    public String healthCheck(){
        return simpleDateFormat.format(System.currentTimeMillis());
    }

}
