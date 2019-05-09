package com.yapp.web1.service;

import com.yapp.web1.domain.User;
import com.yapp.web1.dto.res.ProjectListinUserResDto;

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
     * 임시 유저 데이터 반환
     *
     * @return User.idx==1인 유저 데이터 반환
     */
    User getCurrentUser();

    User findByIdx(Long idx);
}
