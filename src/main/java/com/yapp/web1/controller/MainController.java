package com.yapp.web1.controller;

import com.yapp.web1.domain.Order;
import com.yapp.web1.domain.Project;
import com.yapp.web1.service.OrderService;
import com.yapp.web1.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("main")
public class MainController {

    @Autowired
    ProjectService projectService;
    OrderService orderService;

    //기수별 프로젝트 목록
    @GetMapping("list")
    public List<Project> getProjectList(){
        return projectService.findProjectByOrder();
    }

    //기수 목록 리스트
    @GetMapping("orderList")
    public List<Order> getOrderList(){
        return orderService.findOrder();
    }

    //project 생성
    @PostMapping("create")
    public void createProject(Project project){
        projectService.saveProject(project);
    }

    //project join
    @PostMapping("join")
    public void joinProject(@RequestParam("pro_idx") Long idx){
        //조인테이블...
    }

    //즐겨찾는 프로젝트 목록
    @GetMapping("favorite")
    public List<Project> favoriteProject(){
        // 조인테이블..
    }

    //프로젝트 즐겨찾기 설정 및 해제
    @PostMapping("favorite")
    public void favoriteProject(@RequestParam("pro_idx") Long idx){
        projectService.saveFavoriteProject(idx);
    }
    //프로젝트 상세페이로 가기
    @GetMapping("project")
    public String getProject(@RequestParam("pro_idx") Long idx){
       return "redirect: project" + idx;
    }



}
