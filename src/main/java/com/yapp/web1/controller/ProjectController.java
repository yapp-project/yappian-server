package com.yapp.web1.controller;

import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectListinUserResDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.exception.Common.NoPermissionException;
import com.yapp.web1.exception.Common.NotFoundException;
import com.yapp.web1.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Project Controller
 *
 * @author Jihye Kim
 * @version 1.5
 * @since 0.0.3
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class ProjectController {

    private ProjectService projectService;

    /**
     * 프로젝트 생성
     *
     * @param project 생성할 프로젝트 데이터
     * @param session 로그인 유저 정보
     * @return 생성한 프로젝트 데이터
     * @see /v1/api/project
     */
    @PostMapping("/project")
    @ApiOperation(value = "프로젝트 생성")
    public ResponseEntity<?> createProject(@Valid @RequestBody final ProjectRequestDto project,  @ApiIgnore HttpSession session) {
        try {
            ProjectResponseDto createProjectDto = projectService.createProject(project, 1L);
            return new ResponseEntity<>(createProjectDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 프로젝트 수정
     *
     * @param idx     수정할 프로젝트 idx
     * @param project 수정할 프로젝트 데이터
     * @param session 로그인 유저 session
     * @return 수정한 프로젝트 데이터, 해당 프로젝트의 task 목록
     * @throws FORBIDDENException 프로젝트 조인한 사람만 수정할 수 있다.
     * @throws NOTFOUNDException  프로젝트가 존재해야 수정할 수 있다.
     * @see /v1/api/project/{idx}
     */
    @PutMapping("/project/{idx}")
    @ApiOperation(value = "프로젝트 수정")
    public ResponseEntity<?> updateProject(@PathVariable  @ApiParam(value = "수정할 Project idx", example = "5")  final Long idx,
                                           @Valid @RequestBody final ProjectRequestDto project, HttpSession session) {
        try {
            ProjectResponseDto updateProject = projectService.updateProject(idx, project, 1L);
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
     * @param idx     수정할 프로젝트 idx
     * @param session 로그인 유저 session
     * @throws FORBIDDENException 프로젝트 조인한 사람만 수정할 수 있다.
     * @throws NOTFOUNDException  프로젝트가 존재해야 수정할 수 있다.
     * @see /v1/api/project/{idx}
     */
    @DeleteMapping("/project/{idx}")
    @ApiOperation(value = "프로젝트 삭제")
    public ResponseEntity<?> deleteProject(@PathVariable @ApiParam(value = "삭제할 Project idx", example = "5") final Long idx,
                                           @ApiIgnore HttpSession session) {
        try {
            projectService.deleteProject(idx, 1L);
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
     * @param idx 조회할 프로젝트 idx
     * @see /v1/api/project/{idx}
     */
    @GetMapping("/project/{idx}")
    @ApiOperation(value = "프로젝트 상세 - 프로젝트 정보 및 URL 목록")
    public ResponseEntity<?> getProject(@PathVariable @ApiParam(value = "조회할 projectIdx", example = "5")final Long idx,
                                        @ApiIgnore HttpSession session) {
        try {
            ProjectResponseDto project = projectService.getProject(idx);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * 프로젝트에 조인하기
     *
     * @param projectIdx 조인할 프로젝트 idx
     * @Exception 비밀번호 다름. NoPermissionException
     * @see /v1/api/project/{projectIdx}
     */
    @PostMapping("/project/{projectIdx}")
    @ApiOperation(value = "프로젝트 조인하기")
    public ResponseEntity<?> joinProject(@PathVariable @ApiParam(value = "조인할 projectIdx", example = "5") final Long projectIdx,
                                         String password, @ApiIgnore HttpSession session) {
        try {
            // 추후 user.getIdx로 수정.
            projectService.joinProject(projectIdx, password, 2L);
            return new ResponseEntity<>("해당 프로젝트에 조인 성공", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * 내가 조인한 프로젝트 목록 조회
     *
     * @see /v1/api/user/projects
     */
    @GetMapping("user/projects")
    @ApiOperation(value = "내가 조인한 프로젝트 목록 조회(인증 필요 없음)")
    public ResponseEntity<?> getProjectList(@ApiIgnore HttpSession session) {
        List<ProjectListinUserResDto> projectList = projectService.getProjectList(1L);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    /**
     * 프로젝트에 속한 유저 목록 조회
     *
     * @param idx 조회할 프로젝트 idx
     * @see /v1/api/project/{idx}/users
     */
    @GetMapping("/project/{idx}/users")
    @ApiOperation(value = "프로젝트에 속한 유저 목록 조회(인증 필요 없음)")
    public ResponseEntity<?> getUserListInProject(@PathVariable @ApiParam(value = "조회할 projectIdx", example = "5") final Long idx,
                                                  @ApiIgnore HttpSession session) {
        try {
            List<UserResponseDto> userList = projectService.getUserListInProject(idx);
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 프로젝트 완료 설정
     *
     * @param projectIdx 완료할 프로젝트 idx
     * @throws Exception join한 유저가 아닌 경우
     * @see /v1/api/project/{idx}/finish
     *
     * pdf만 올리기는 추후 수정.
     */
    @PutMapping("/project/{projectIdx}/finish")
    @ApiOperation(value = "프로젝트 완료 설정")
    public ResponseEntity<?> setFinishedProject(@PathVariable @ApiParam(value = "완료할 projectIdx", example = "5") final Long projectIdx,
                                                @RequestParam("files") MultipartFile[] multipartFiles,
                                                @Valid FinishProjectRequestDto project,
                                                @ApiIgnore HttpSession session) {
        FinishProjectResponseDto finishProjectResponseDto = null;
        try {
            finishProjectResponseDto = projectService.setFinishedProject(projectIdx, multipartFiles, project, 1L);
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
     * @see /v1/api/project/{idx}/finish
     */
    @GetMapping("/project/{projectIdx}/finish")
    @ApiOperation(value = "프로젝트 완료 상세")
    public ResponseEntity<?> getFinishedProject(@PathVariable @ApiParam(value = "조회할 projectIdx", example = "5") final Long projectIdx,
                                                @ApiIgnore HttpSession session) {
        try {
            FinishProjectResponseDto project = projectService.getFinishedProject(projectIdx);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
