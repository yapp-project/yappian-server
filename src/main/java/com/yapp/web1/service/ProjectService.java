package com.yapp.web1.service;

import com.yapp.web1.domain.Project;
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
     * @param project 생성할 Project 정보 - 추후 request dto로 변경(User, Orders, Project)
     * @param session 로그인 유저 session
     * @return 생성한 Project 정보 - 추후 response dto로 변경(Project, Task)
     *
     * @exception Exception 같은 기수 다른 Project에 join된 경우 - 추후 수정
     */
    Project createProject(Project project, HttpSession session);

    /**
     * 프로젝트 수정
     *
     * @param idx 수정할 Project idx
     * @param project 수정할 Project 정보 - 추후 dto로 변경(Orders, Project)
     */
    boolean updateProject(Long idx, Project project);

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
