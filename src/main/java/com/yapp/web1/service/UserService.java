package com.yapp.web1.service;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.User;
import com.yapp.web1.dto.res.ProjectListResponseDto;

import java.util.List;

/**
 * UserService Interface
 *
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.1
 */
public interface UserService {

    /**
     * 즐겨찾는 프로젝트 목록
     *
     * @param user 로그인 유저
     * @return 즐겨찾기 Project list - 추후 dto로 수정 (Project, Orders, favorite, joined)
     */
    List<ProjectListResponseDto> getFavoriteProjects(User user);

    /**
     * 프로젝트 즐겨찾기 설정 및 해제
     *
     * @param idx 즐겨찾기 설정할 프로젝트 idx
     * @param user 로그인 유저
     */
    boolean setFavoriteProject(Long idx, User user);

    /**
     * 프로젝트 참여
     *
     * @param idx 참여할 Project idx
     * @param user 로그인 유저
     * @return 참여한 Project 정보 - 추후 response dto로 변경(Project, Url)
     *
     * @exception Exception 같은 기수 다른 Project에 join된 경우 - 추후 수정
     */
    Project joinProject(Long idx, User user);

    /**
     * 임시 유저 데이터 반환
     *
     * @return User.idx==1인 유저 데이터 반환
     */
    User getCurrentUser();

    User findByIdx(Long idx);
}
