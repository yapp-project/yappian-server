package com.yapp.web1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api")
@Api(tags = "헬스체크 API")
public class HealthCheckController {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

    @GetMapping("/_hcheck")
    @ApiOperation(value = "서버 체크")
    public String healthCheck(){
        return simpleDateFormat.format(System.currentTimeMillis());
    }

}
