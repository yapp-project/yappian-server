package com.yapp.web1.controller;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.service.OrderService;
import com.yapp.web1.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Project Controller
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.2
 * @version 1.0
 */
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    OrderService orderService;

    /**
     * 프로젝트 상세 페이지
     * @param project_idx, user_idx
     *
     * @see /v1/api/project
     */
    @GetMapping("/project")
    @ResponseStatus(value = HttpStatus.OK)//200.(요청이 성공적으로 되었습니다.리소스를 불러와서 메시지 바디에 전송되었습니다.)
    public void getProject(@PathVariable final Long projectIdx, @PathVariable final Long userIdx){
        //프로젝트 상세 페이지 task list..?
    }

    /**
     * 프로젝트 생성 팝업 get
     *
     * @param userIdx(session)
     * @see /v1/api/project
     */
    @GetMapping("/popProject")//url고민좀..
    @ResponseStatus(value = HttpStatus.OK)//200.(요청이 성공적으로 되었습니다.리소스를 불러와서 메시지 바디에 전송되었습니다.)
    public List<Orders> getCreateProject(){
        return orderService.findNumber();  //기수 선택
        //플랫폼 선택 Enum converter,,,projectType
    }

    /**
     * 프로젝트 생성 팝업 post
     *
     * @param project 생성할 프로젝트 데이터, useridx 프로젝트 생성자Idx(session)
     * @exception Exception 이미 join된 유저 - 추후 수정
     *
     * @see /v1/api/project
     */
    @PostMapping("/project/{userIdx}")
    @ResponseStatus(value = HttpStatus.CREATED)//201. (요청이 성공적이었으며 그 결과로 새로운 리소스가 생성)
    public String createProject(@RequestBody final Project project,@PathVariable final Long userIdx){
       long projectIdx = projectService.insertProject(project,userIdx);
       System.out.println("project 생성Idx : "+projectIdx);
        return "redirect:/v1/api/project/{projectIdx}/{userIdx}";
    }

    /**
     * 프로젝트 수정
     *
     * @param idx 수정할 프로젝트 idx
     * @param project 수정할 프로젝트 데이터
     * @exception Exception invalid project idx - 추후 수정
     *
     * @see /v1/api/project/{idx}
     */
    @PutMapping("/project/{idx}")
    public ResponseEntity editProject(@PathVariable final Long idx, @Valid @RequestBody final Project project){
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 프로젝트 삭제
     *
     * @param idx 수정할 프로젝트 idx
     * @exception Exception Project.createUserIdx와 세션 User idx 불일치시 - 추후 수정
     *
     * @see /v1/api/project/{idx}
     */
    @DeleteMapping("project/{idx}")
    public ResponseEntity deleteProject(@PathVariable final Long idx){
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
