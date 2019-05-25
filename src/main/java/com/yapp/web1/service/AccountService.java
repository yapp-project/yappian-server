package com.yapp.web1.service;

import com.yapp.web1.domain.Account;
import com.yapp.web1.dto.res.ProjectListInAccountResDto;
import com.yapp.web1.social.AccountConnection;

import java.util.List;

/**
 * AccountService Interface
 *
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.1
 */
public interface AccountService {

    /**
     * 유저가 조인한 프로젝트 목록 조회
     */
    List<ProjectListInAccountResDto> getProjectList(Long accountIdx);

    /**
     * 회원가입. 유저 데이터 저장 후 리턴
     * @param accountConnection
     * @return 저장한 Account 데이터
     */
    Account signUp(AccountConnection accountConnection);

    /**
     * 가입된 유저 데이터 조회
     * @param accountConnection
     * @return 찾은 Account 데이터
     */
    Account findBySocial(AccountConnection accountConnection);

    /**
     * 유저 가입 체크
     * @param accountConnection
     * @return 가입 여부
     */
    boolean isExistUser(AccountConnection accountConnection);

    /**
     * 임시 유저 데이터 반환
     *
     * @return Account.idx==1인 유저 데이터 반환
     */
    Account getCurrentUser();

    Account findByIdx(Long idx);
}
