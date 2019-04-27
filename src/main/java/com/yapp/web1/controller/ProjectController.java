package com.yapp.web1.controller;

import com.yapp.web1.domain.User;
import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.service.ProjectService;
import com.yapp.web1.service.UserService;
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
 * @version 1.5
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;
    /**
     * 프로젝트 생성
     *
     * @param project 생성할 프로젝트 데이터
     * @param session 로그인 유저 정보
     * @return 생성한 프로젝트 데이터, 해당 프로젝트의 task 목록
     *
     * @exception Exception 이미 join된 유저 - 추후 수정
     *
     * @see /v1/api/project
     */
    /*
    @PostMapping("/project")
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody final ProjectRequestDto project, HttpSession session){
        User user = userService.getCurrentUser();
        ProjectResponseDto createProject = projectService.createProject(project,user.getIdx());
        return new ResponseEntity<>(createProject, HttpStatus.CREATED);//201. (요청이 성공적이었으며 그 결과로 새로운 리소스가 생성)
    }
*/
    /**
     * 프로젝트 수정
     *
     * @param idx 수정할 프로젝트 idx
     * @param project 수정할 프로젝트 데이터
     * @param session 로그인 유저 session
     * @return 수정한 프로젝트 데이터, 해당 프로젝트의 task 목록
     *
     * @see /v1/api/project/{idx}
     */
    /*
    @PutMapping("/project/{idx}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable final Long idx, @Valid @RequestBody final ProjectRequestDto project, HttpSession session){
        ProjectResponseDto updateProject = projectService.updateProject(idx, project, 201632025L); // 테스트 확인하려고.원래 User user
        return new ResponseEntity<>(updateProject, HttpStatus.OK);
    }
    */

    /**
     * 프로젝트 삭제
     *
     * @param idx 수정할 프로젝트 idx
     * @param session 로그인 유저 session
     *
     * @see /v1/api/project/{idx}
     */
    @DeleteMapping("/project/{idx}")
    public ResponseEntity deleteProject(@PathVariable final Long idx, HttpSession session){
        projectService.deleteProject(idx, 201632025L);//테스트 확인하려고.원래 User user
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 프로젝트 상세
     * 프로젝트 정보 및 Url 목록
     * @param idx 조회할 프로젝트 idx
     * @exception Exception invalid idx - 추후 수정
     *
     * @see /v1/api/project/{idx}
     */
/*
    @GetMapping("/project/{idx}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable final Long idx){
        ProjectResponseDto project = projectService.getProject(idx);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
*/
    /**
     * 프로젝트 완료 설정
     *
     * @param idx 완료할 프로젝트 idx
     * @exception Exception 이미 완료된 경우, join한 유저가 아닌 경우
     *
     * @see /v1/api/project/{idx}/finish
     */
    @PutMapping("/project/{idx}/finish")
    public ResponseEntity setFinishedProject(@PathVariable final Long idx, @Valid @RequestBody FinishProjectRequestDto project){
        projectService.setFinishedProject(idx, project);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 프로젝트 완료 상세
     *
     * @param idx 조회할 프로젝트 idx
     * @exception Exception 완료되지 않은 경우
     *
     * @see /v1/api/project/{idx}/finish
     */
    @GetMapping("/project/{idx}/finish")
    public ResponseEntity<FinishProjectResponseDto> getFinishedProject(@PathVariable final Long idx) {
        FinishProjectResponseDto project = projectService.getFinishedProject(idx);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    /**
     * 프로젝트에 속한 유저 목록 조회
     *
     * @param idx 조회할 프로젝트 idx
     *
     * @see /v1/api/project/{idx}/users
     */
    @GetMapping("/project/{idx}/users")
    public ResponseEntity<List<UserResponseDto>> getUserListInProject(@PathVariable final Long idx) {
        List<UserResponseDto> userList = projectService.getUserListInProject(idx);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

}
