package com.yapp.web1.controller;

import com.yapp.web1.common.AuthUtils;
import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.AccountResponseDto;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.ProjectsResDto;
import com.yapp.web1.exception.NoPermissionException;
import com.yapp.web1.exception.NotFoundException;
import com.yapp.web1.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Project Controller
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @version 1.6
 * @since 0.0.4
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api")
@RestController
@Api(tags = "프로젝트 APIs")
public class ProjectController {

    private ProjectService projectService;

    /**
     * 프로젝트 목록
     * FE 디버깅용 전체 공개 API
     *
     * @see /v1/api/projects
     */
    @GetMapping("/projects")
    @ApiOperation(value="프로젝트 리스트")
    public ResponseEntity<?> getProjects(){
        try{
            List<ProjectsResDto> projectsResDto = projectService.getProjects();
            return new ResponseEntity<>(projectsResDto, HttpStatus.OK);
        }catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 프로젝트 생성
     *
     * @param project 생성할 프로젝트 데이터
     * @return 생성한 프로젝트 데이터
     * @see /api/project
     */
    @PostMapping("/project")
    @ApiOperation(value = "프로젝트 생성(인증 필요)")
    public ResponseEntity<?> createProject(@Valid @RequestBody final ProjectRequestDto project) {
        try {
            ProjectResponseDto createProjectDto = projectService.createProject(project, AuthUtils.getCurrentAccount().getIdx());
            return new ResponseEntity<>(createProjectDto, HttpStatus.CREATED);
        } catch (NoPermissionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 프로젝트 수정
     *
     * @param projectIdx     수정할 프로젝트 idx
     * @param project       수정할 프로젝트 데이터
     * @return 수정한 프로젝트 데이터, 해당 프로젝트의 task 목록
     * @throws NoPermissionException 프로젝트 조인한 사람만 수정할 수 있다.
     * @throws NotFoundException  프로젝트가 존재해야 수정할 수 있다.
     * @see /api/project/{projectIdx}
     */
    @PutMapping("/project/{projectIdx}")
    @ApiOperation(value = "프로젝트 수정(참여 유저)")
    public ResponseEntity<?> updateProject(@PathVariable  @ApiParam(value = "수정할 Project idx", example = "1")  final Long projectIdx,
                                           @Valid @RequestBody final ProjectRequestDto project) {
        try {
            projectService.updateProject(projectIdx, project, AuthUtils.getCurrentAccount().getIdx());
            return new ResponseEntity<>("프로젝트 수정 성공", HttpStatus.OK);
        } catch (NoPermissionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 프로젝트 삭제
     *
     * @param projectIdx    수정할 프로젝트 idx
     * @throws NoPermissionException 프로젝트 조인한 사람만 수정할 수 있다.
     * @throws NotFoundException  프로젝트가 존재해야 수정할 수 있다.
     * @see /api/project/{projectIdx}
     */
    @DeleteMapping("/project/{projectIdx}")
    @ApiOperation(value = "프로젝트 삭제(참여 유저)")
    public ResponseEntity<?> deleteProject(@PathVariable @ApiParam(value = "삭제할 Project idx", example = "1") final Long projectIdx) {
        try {
            projectService.deleteProject(projectIdx, AuthUtils.getCurrentAccount().getIdx());
            return new ResponseEntity<>("Project 삭제 성공", HttpStatus.NO_CONTENT);
        } catch (NoPermissionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 프로젝트 상세
     * 프로젝트 정보 및 Url 목록
     *
     * @param projectIdx 조회할 프로젝트 idx
     * @see /api/project/{projectIdx}
     */
    @GetMapping("/project/{projectIdx}")
    @ApiOperation(value = "프로젝트 상세 - 프로젝트 정보 및 URL 목록(인증 필요 없음)")
    public ResponseEntity<?> getProject(@PathVariable @ApiParam(value = "조회할 projectIdx", example = "1")final Long projectIdx) {
        try {
            ProjectResponseDto project = projectService.getProject(projectIdx);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 프로젝트 참여
     *
     * @param projectIdx 참여할 프로젝트 idx
     * @exception NoPermissionException 비밀번호 다름
     * @exception NoPermissionException 권한 없음(비회원)
     * @exception Exception 이미 join된 유저 - 추후 수정
     *
     * @see /api/join/{projectIdx}
     */
    @PostMapping("/project/{projectIdx}")
    @ApiOperation(value = "프로젝트 조인하기(인증 필요)")
    public ResponseEntity<?> joinProject(@PathVariable @ApiParam(value = "조인할 projectIdx", example = "1") final Long projectIdx, String password) {
        try {
            projectService.joinProject(projectIdx, password, AuthUtils.getCurrentAccount().getIdx());
            return new ResponseEntity<>("해당 프로젝트에 조인 성공", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    // TODO 프로젝트 조인 취소 구현



    /**
     * 프로젝트에 속한 유저 목록 조회
     *
     * @param projectIdx 조회할 프로젝트 idx
     * @see /api/project/{projectIdx}/users
     */
    @GetMapping("/project/{projectIdx}/users")
    @ApiOperation(value = "프로젝트에 속한 유저 목록 조회(인증 필요 - 우선 인증 없음)")
    public ResponseEntity<?> getAccountListInProject(@PathVariable @ApiParam(value = "조회할 projectIdx", example = "1") final Long projectIdx) {
        try {
            List<AccountResponseDto> accountList = projectService.getAccountListInProject(projectIdx);
            return new ResponseEntity<>(accountList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 프로젝트 완료 설정
     *
     * @param projectIdx 완료할 프로젝트 idx
     * @throws Exception join한 유저가 아닌 경우
     * @see /api/project/{projectIdx}/finish
     *
     */
    // TODO pdf만 올리기는 추후 수정.
    @PutMapping("/project/{projectIdx}/finish")
    @ApiOperation(value = "프로젝트 완료 설정(참여 유저)")
    public ResponseEntity<?> setFinishedProject(@PathVariable @ApiParam(value = "완료할 projectIdx", example = "1") final Long projectIdx,
                                                @RequestParam("files") MultipartFile[] multipartFiles,
                                                @Valid FinishProjectRequestDto project) {
        try {
            FinishProjectResponseDto finishProjectResponseDto = projectService.setFinishedProject(projectIdx, multipartFiles, project, AuthUtils.getCurrentAccount().getIdx());
            return new ResponseEntity<>(finishProjectResponseDto, HttpStatus.OK);
        } catch (NoPermissionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 프로젝트 완료 상세
     *
     * @param projectIdx 조회할 프로젝트 idx
     * @throws Exception NOT_FOUND
     * @see /api/project/{projectIdx}/finish
     */
    @GetMapping("/project/{projectIdx}/finish")
    @ApiOperation(value = "프로젝트 완료 상세(인증 필요 없음)")
    public ResponseEntity<?> getFinishedProject(@PathVariable @ApiParam(value = "조회할 projectIdx", example = "1") final Long projectIdx) {
        try {
            FinishProjectResponseDto project = projectService.getFinishedProject(projectIdx);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
