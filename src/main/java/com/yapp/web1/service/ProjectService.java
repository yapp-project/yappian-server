package com.yapp.web1.service;

import com.yapp.web1.domain.Project;
import com.yapp.web1.dto.req.ProjectSaveRequestDto;
import com.yapp.web1.dto.res.ProjectSaveResponseDto;

import javax.servlet.http.HttpSession;

/**
 * ProjectService Interface
 *
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.0
 */
public interface ProjectService {
    /**
     * 프로젝트 생성
     *
     * @param project 생성할 Project 정보
     * @param session 로그인 유저 session
     * @return 생성한 Project 정보
     *
     * @exception Exception 같은 기수 다른 Project에 join된 경우 - 추후 수정
     */
    ProjectSaveResponseDto createProject(ProjectSaveRequestDto project, HttpSession session);

    /**
     * 프로젝트 수정
     *
     * @param idx 수정할 Project idx
     * @param project 수정할 Project 정보
     */
    boolean updateProject(Long idx, ProjectSaveRequestDto project);

    /**
     * 프로젝트 삭제
     *
     * @param idx 삭제할 Project idx
     * @param session 로그인 유저 session
     *
     * @exception Exception Project의 createUserIdx와 session userIdx 불일치 시 삭제 불가
     */
    boolean deleteProject(Long idx, HttpSession session);
}
