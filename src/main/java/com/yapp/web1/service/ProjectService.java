package com.yapp.web1.service;

import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.AccountResponseDto;
import com.yapp.web1.dto.res.ProjectsResDto;
import com.yapp.web1.exception.NoPermissionException;
import com.yapp.web1.exception.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ProjectService Interface
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.4
 * @version 1.3
 */
public interface ProjectService {

    /**
     * 프로젝트 리스트 - 전체 내용
     */
    List<ProjectsResDto> getProjects();

    /**
     * 프로젝트 생성
     *
     * @param dto 생성할 Project 정보
     * @param accountIdx 로그인 유저
     * @return 생성한 Project 정보
     */
    ProjectResponseDto createProject(ProjectRequestDto dto, Long accountIdx);//실제로는 Account

    /**
     * 프로젝트 수정
     *
     * @param idx 수정할 Project idx
     * @param dto 수정할 Project 정보
     * @param accountIdx 로그인 유저
     * @exception NoPermissionException 프로젝트 조인한 사람만 수정할 수 있다.
     * @exception NotFoundException 프로젝트가 존재해야 수정할 수 있다.
     */
    ProjectResponseDto updateProject(Long idx, ProjectRequestDto dto, Long accountIdx);//실제로는 Account

    /**
     * 프로젝트 삭제
     *
     * @param idx 삭제할 Project idx
     * @param accountIdx 로그인 유저
     * @exception NoPermissionException 프로젝트 조인한 사람만 수정할 수 있다.
     * @exception NotFoundException 프로젝트가 존재해야 수정할 수 있다.
     */
    void deleteProject(Long idx, Long accountIdx);//실제로는 Account

    /**
     * 프로젝트 상세
     * 프로젝트 정보 및 Url 목록
     * @param idx 조회할 프로젝트 idx
     * @see /v1/api/project/{idx}
     */
    ProjectResponseDto getProject(Long idx);

    /**
     * 프로젝트에 참여
     *
     * @param projectIdx 참여할 Project idx
     * @see /v1/api/project/{projectIdx}
     */
    void joinProject(Long projectIdx, String password, Long randomAccount);

    /**
     * 프로젝트에 속한 유저 목록 조회
    *
    * @param idx 조회할 Project idx
     */
    List<AccountResponseDto> getAccountListInProject(Long idx);

    /**
     * 프로젝트 완료 설정
     * 프로젝트 완료 정보 반영 후 finalCheck 변경해야 함 - finishedProject()
     *
     * @param projectIdx 완료할 Project idx
     * @param dto 프로젝트 완료 정보
     *
     * @exception Exception noPermission, notFound
     */
    FinishProjectResponseDto setFinishedProject(Long projectIdx, MultipartFile[] multipartFiles, FinishProjectRequestDto dto, Long accountIdx);

    /**
     * 프로젝트 완료 조회
     *
     * @param idx 조회할 Project idx
     *
     * @exception Exception 완료되지 않은 경우 조회 불가
     */
    FinishProjectResponseDto getFinishedProject(Long idx);

    /**
     * 프로젝트 나가기
     * @param projectIdx
     * @param accountIdx
     */
    void leaveProject(Long projectIdx, Long accountIdx);
}
