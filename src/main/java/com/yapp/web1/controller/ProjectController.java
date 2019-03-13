package com.yapp.web1.controller;

import com.yapp.web1.domain.Project;
import com.yapp.web1.dto.req.ProjectSaveRequestDto;
import com.yapp.web1.dto.res.ProjectSaveResponseDto;
import com.yapp.web1.service.impl.ProjectServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Project Controller
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.1
 */
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class ProjectController {

    private ProjectServiceImpl projectServiceImpl;

    /**
     * 프로젝트 생성
     *
     * @param project 생성할 프로젝트 데이터
     * @return 생성한 프로젝트 데이터, 해당 프로젝트의 task 목록
     * @exception Exception 이미 join된 유저 - 추후 수정
     *
     * @see /v1/api/project
     */
    @PostMapping("/project")
    public ResponseEntity<ProjectSaveResponseDto> createProject(@Valid @RequestBody final ProjectSaveRequestDto project, HttpSession session){
        ProjectSaveResponseDto createProject = projectServiceImpl.createProject(project, session);
        return new ResponseEntity<>(createProject, HttpStatus.CREATED);
    }

    /**
     * 프로젝트 수정
     *
     * @param idx 수정할 프로젝트 idx
     * @param project 수정할 프로젝트 데이터
     * @exception Exception Project.createUserIdx와 세션 User idx 불일치시 - 추후 수정
     *
     * @see /v1/api/project/{idx}
     */
    @PutMapping("/project/{idx}")
    public ResponseEntity updateProject(@PathVariable final Long idx, @Valid @RequestBody final ProjectSaveRequestDto project){
        projectServiceImpl.updateProject(idx, project);
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
    public ResponseEntity deleteProject(@PathVariable final Long idx, HttpSession session){
        projectServiceImpl.deleteProject(idx, session);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
