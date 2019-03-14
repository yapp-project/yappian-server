package com.yapp.web1.service;

import com.yapp.web1.domain.User;
import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.ProjectSaveResponseDto;

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
     * 프로젝트 생성 get
     * @see /v1/api/project
     */
    ProjectSaveResponseDto saveProject();

    /**
     * 프로젝트 생성 post
     *
     * @param dto 생성할 Project 정보
     * @param user 로그인 유저
     * @return 생성한 Project 정보
     *
     * @exception Exception 같은 기수 다른 Project에 join된 경우 - 추후 수정
     */
    ProjectResponseDto createProject(ProjectRequestDto dto, Long userIdx);

    /**
     * 프로젝트 수정
     *
     * @param idx 수정할 Project idx
     * @param dto 수정할 Project 정보
     * @param user 로그인 유저
     *
     * @exception Exception Project의 createUserIdx와 session userIdx 불일치 시 삭제 불가
     */
    ProjectResponseDto updateProject(Long idx, ProjectRequestDto dto, User user);

    /**
     * 프로젝트 삭제
     *
     * @param idx 삭제할 Project idx
     * @param user 로그인 유저
     *
     * @exception Exception Project의 createUserIdx와 session userIdx 불일치 시 삭제 불가
     */
    boolean deleteProject(Long idx, User user);

    /**
     * 프로젝트 조회
     *
     * @param idx 조회할 Project idx
     * @exception Exception invalid idx
     */
    ProjectResponseDto getProject(Long idx);

    /**
     * 프로젝트 완료 설정
     * 프로젝트 완료 정보 반영 후 finalCheck 변경해야 함 - finishedProject()
     *
     * @param idx 완료할 Project idx
     * @param dto 프로젝트 완료 정보
     *
     * @exception Exception 이미 완료된 경우
     */
    boolean setFinishedProject(Long idx, FinishProjectRequestDto dto);

    /**
     * 프로젝트 완료 조회
     *
     * @param idx 조회할 Project idx
     *
     * @exception Exception 완료되지 않은 경우 조회 불가
     */
    FinishProjectResponseDto getFinishedProject(Long idx);

}
