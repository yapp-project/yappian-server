package com.yapp.web1.controller;

import com.yapp.web1.dto.res.OrdersResponseDto;

import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.service.OrdersService;
import com.yapp.web1.service.ProjectService;
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
 * @version 1.4
 */
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class ProjectController {

    private ProjectService projectService;
    private OrdersService ordersService;

    /**
     * 프로젝트 생성 팝업 get. 기수목록 필요.
     * @see /v1/api/project
     */
    @GetMapping("/project")
    public ResponseEntity<List<OrdersResponseDto>> getCreateProject(){
        List<OrdersResponseDto> orderList = ordersService.getOrderList();
        return new ResponseEntity<>(orderList, HttpStatus.OK); //200.(요청이 성공적으로 되었습니다.리소스를 불러와서 메시지 바디에 전송되었습니다.)
    }


    /**
     * 프로젝트 생성 팝업 post
     * @exception Exception 이미 join된 유저 - 추후 수정
     * @return 생성한 프로젝트 데이터, 해당 프로젝트의 task 목록
     * @see /v1/api/project
     */

    @PostMapping("/project")
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody final ProjectRequestDto project, HttpSession session){
         ProjectResponseDto createProject = projectService.createProject(project,1111L);
        // ProjectResponseDto createProject = projectService.createProject(project,Long.parseLong(session.getId()));
        return new ResponseEntity<>(createProject, HttpStatus.CREATED);//201. (요청이 성공적이었으며 그 결과로 새로운 리소스가 생성)

    }

    /**
     * 프로젝트 수정
     *
     * @param idx 수정할 프로젝트 idx
     * @param project 수정할 프로젝트 데이터
     * @param session 로그인 유저 session
     * @return 수정한 프로젝트 데이터, 해당 프로젝트의 task 목록
     *
     * @exception Exception Project.createUserIdx와 세션 User idx 불일치시 - 추후 수정
     *
     * @see /v1/api/project/{idx}
     */
    @PutMapping("/project/{idx}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable final Long idx, @Valid @RequestBody final ProjectRequestDto project, HttpSession session){
        ProjectResponseDto updateProject = projectService.updateProject(idx, project, 1111L);
        return new ResponseEntity<>(updateProject, HttpStatus.OK);
    }

    /**
     * 프로젝트 삭제
     *
     * @param idx 수정할 프로젝트 idx
     * @param session 로그인 유저 session
     * @exception Exception Project.createUserIdx와 세션 User idx 불일치시 - 추후 수정
     *
     * @see /v1/api/project/{idx}
     */
    @DeleteMapping("/project/{idx}")
    public ResponseEntity deleteProject(@PathVariable final Long idx, HttpSession session){
        projectService.deleteProject(idx, null);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 프로젝트 상세
     * : 프로젝트 정보 및 Task 목록
     *
     * @param idx 조회할 프로젝트 idx
     * @exception Exception invalid idx - 추후 수정
     *
     * @see /v1/api/project/{idx}
     */

    @GetMapping("/project/{idx}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable final Long idx){
        ProjectResponseDto project = projectService.getProject(idx);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

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
}
