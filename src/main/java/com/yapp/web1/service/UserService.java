package com.yapp.web1.service;

import com.yapp.web1.domain.User;
import com.yapp.web1.dto.res.ProjectListinUserResDto;
import com.yapp.web1.social.UserConnection;

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
     * 유저가 조인한 프로젝트 목록 조회
     */
    List<ProjectListinUserResDto> getProjectList(Long userIdx);

    /**
     * 회원가입. 유저 데이터 저장 후 리턴
     * @param userConnection
     * @return 저장한 User 데이터
     */
    User signUp(UserConnection userConnection);

    /**
     * 가입된 유저 데이터 조회
     * @param userConnection
     * @return 찾은 User 데이터
     */
    User findBySocial(UserConnection userConnection);

    /**
     * 유저 가입 체크
     * @param userConnection
     * @return 가입 여부
     */
    boolean isExistUser(UserConnection userConnection);

    /**
     * 임시 유저 데이터 반환
     *
     * @return User.idx==1인 유저 데이터 반환
     */
    User getCurrentUser();

    User findByIdx(Long idx);
}
