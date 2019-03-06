package com.yapp.web1.controller;

import com.yapp.web1.domain.Project;
import com.yapp.web1.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    private final ProjectService projectService;

    /**
     * 프로젝트 생성
     *
     * @param project 생성할 프로젝트 데이터
     * @exception Exception 이미 join된 유저 - 추후 수정
     *
     * @see /v1/api/project
     */
    @PostMapping("/project")
    public ResponseEntity createProject(@RequestBody final Project project){
//        projectService.saveProject(project);
        return new ResponseEntity(HttpStatus.CREATED);
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
