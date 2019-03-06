package com.yapp.web1.controller;

import com.yapp.web1.domain.Project;
import com.yapp.web1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    //즐겨찾는 프로젝트 목록
    @GetMapping("favorite")
    public List<Project> favoriteProject(){
        // 조인테이블..
    }
}
