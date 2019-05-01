package com.yapp.web1.service;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.User;
import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.ApiResponse;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UserResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ProjectService Interface
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.2
 */
public interface ProjectService {

    /**
     * 프로젝트 생성
     *
     * @param dto 생성할 Project 정보
     * @param userIdx 로그인 유저
     * @return 생성한 Project 정보
     */
    ProjectResponseDto createProject(ProjectRequestDto dto, Long userIdx);//실제로는 User user

    /**
     * 프로젝트 수정
     *
     * @param idx 수정할 Project idx
     * @param dto 수정할 Project 정보
     * @param userIdx 로그인 유저
     * @exception FORBIDDENException 프로젝트 조인한 사람만 수정할 수 있다.
     * @exception NOTFOUNDException 프로젝트가 존재해야 수정할 수 있다.
     */
    ProjectResponseDto updateProject(Long idx, ProjectRequestDto dto, Long userIdx);//실제로는 User user

    /**
     * 프로젝트 삭제
     *
     * @param idx 삭제할 Project idx
     * @param user 로그인 유저
     * @exception FORBIDDENException 프로젝트 조인한 사람만 수정할 수 있다.
     * @exception NOTFOUNDException 프로젝트가 존재해야 수정할 수 있다.
     */
    void deleteProject(Long idx, Long userIdx);//실제로는 User user

    /**
     * 프로젝트 상세
     * 프로젝트 정보 및 Url 목록
     * @param idx 조회할 프로젝트 idx
     * @see /v1/api/project/{idx}
     */
    ProjectResponseDto getProject(Long idx);

    /**
     * 프로젝트에 조인하기
     *
     * @param projectIdx 조회할 프로젝트 idx
     * @see /v1/api/project/{projectIdx}
     */
    void joinProject(Long projectIdx, Long randomUser);

    /**
     * 프로젝트에 속한 유저 목록 조회
    *
    * @param idx 조회할 Project idx
     */
    List<UserResponseDto> getUserListInProject(Long idx);

    /**
     * 프로젝트 완료 설정
     * 프로젝트 완료 정보 반영 후 finalCheck 변경해야 함 - finishedProject()
     *
     * @param projectIdx 완료할 Project idx
     * @param dto 프로젝트 완료 정보
     *
     * @exception Exception noPermission, notFound
     */
    void setFinishedProject(Long projectIdx, MultipartFile[] multipartFiles, FinishProjectRequestDto dto, Long userIdx);

    /**
     * 프로젝트 완료 조회
     *
     * @param idx 조회할 Project idx
     *
     * @exception Exception 완료되지 않은 경우 조회 불가
     */
    FinishProjectResponseDto getFinishedProject(Long idx);
}
