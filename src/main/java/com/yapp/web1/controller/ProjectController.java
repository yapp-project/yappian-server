package com.yapp.web1.controller;

import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.ApiResponse;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.exception.Common.NoPermissionException;
import com.yapp.web1.exception.Common.NotFoundException;
import com.yapp.web1.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ApiResponse<?> createProject(@Valid @RequestBody final ProjectRequestDto project, HttpSession session) {
        ProjectResponseDto createProjectDto = null;

        try {
            createProjectDto = projectService.createProject(project, 1L);
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED)
                    .message("프로젝트 생성 성공")
                    .data(createProjectDto)
                    .build();

        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("bad request")
                    .build();
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
    public ApiResponse<?> updateProject(@PathVariable final Long idx, @Valid @RequestBody final ProjectRequestDto project, HttpSession session) {
        ProjectResponseDto updateProject = null;
        try {
            updateProject = projectService.updateProject(idx, project, 1L);
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("프로젝트 수정 성공")
                    .data(updateProject)
                    .build();
        } catch (NotFoundException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(e.getMessage())
                    .build();
        } catch (NoPermissionException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .build();
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
    public ApiResponse<?> deleteProject(@PathVariable final Long idx, HttpSession session) {
        try {
            projectService.deleteProject(idx, 1L);
            return ApiResponse.builder()
                    .status(HttpStatus.NO_CONTENT)
                    .message("Project 삭제 성공")
                    .build();

        } catch (NoPermissionException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .build();
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
    public ApiResponse<?> getProject(@PathVariable final Long idx, HttpSession session) {

        try {
            ProjectResponseDto project = projectService.getProject(idx);
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("가져오기 성공")
                    .data(project)
                    .build();
        } catch (NotFoundException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(e.getMessage())
                    .build();
        }

    }

    /**
     * 프로젝트에 조인하기
     *
     * @param projectIdx 조회할 프로젝트 idx
     * @see /v1/api/project/{projectIdx}
     */
    @PostMapping("/project/{projectIdx}")
    public ApiResponse<?> joinProject(@PathVariable final Long projectIdx, HttpSession session) {
        try {
            // 추후 user.getIdx로 수정.
            projectService.joinProject(projectIdx,1L);

            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("해당 프로젝트에 조인 성공")
                    .build();

        } catch (NotFoundException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(e.getMessage())
                    .build();
        }
    }

    /**
     * 프로젝트에 속한 유저 목록 조회
     *
     * @param idx 조회할 프로젝트 idx
     * @see /v1/api/project/{idx}/users
     */
    @GetMapping("/project/{idx}/users")
    public ApiResponse<?> getUserListInProject(@PathVariable final Long idx) {
        try {
            List<UserResponseDto> userList = projectService.getUserListInProject(idx);
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("유저 목록 조회 성공")
                    .data(userList)
                    .build();
        } catch (NotFoundException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(e.getMessage())
                    .build();
        }
    }

    /**
     * 프로젝트 완료 설정
     *
     * @param projectIdx 완료할 프로젝트 idx
     * @throws Exception join한 유저가 아닌 경우
     * @see /v1/api/project/{idx}/finish
     */
    @PutMapping("/project/{projectIdx}/finish")
    public ApiResponse<?> setFinishedProject(@PathVariable final Long projectIdx, @RequestParam("files")MultipartFile[] multipartFiles,
                                             @Valid @RequestBody FinishProjectRequestDto project) {
       try{
           projectService.setFinishedProject(projectIdx, multipartFiles, project, 1L);
           return ApiResponse.builder()
                   .status(HttpStatus.OK)
                   .message("프로젝트 완료 설정 성공")
                   .build();
       }catch (NoPermissionException e){
           return ApiResponse.builder()
                   .status(HttpStatus.FORBIDDEN)
                   .message(e.getMessage())
                   .build();
       }catch(NotFoundException e){
           return ApiResponse.builder()
                   .status(HttpStatus.NOT_FOUND)
                   .message(e.getMessage())
                   .build();
       }
    }

    /**
     * 프로젝트 완료 상세
     *
     * @param idx 조회할 프로젝트 idx
     * @throws Exception 완료되지 않은 경우
     * @see /v1/api/project/{idx}/finish
     */
    @GetMapping("/project/{idx}/finish")
    public ResponseEntity<FinishProjectResponseDto> getFinishedProject(@PathVariable final Long idx) {
        FinishProjectResponseDto project = projectService.getFinishedProject(idx);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
}
