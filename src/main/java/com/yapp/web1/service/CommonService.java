package com.yapp.web1.service;

import com.yapp.web1.domain.Account;
import com.yapp.web1.domain.File;
import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.dto.res.AccountResponseDto;

import java.util.List;

public interface CommonService {

    /**
     * 해당 엔티티 있는지 확인
     * @param idx
     * @return Project, Orders, Account
     */
    Project findById(Long idx);
    Orders findOrdersById(Long idx);
    Account findAccountById(Long idx);
    File findFileById(Long idx);

    /**
     * 해당 프로젝트에 join된 accountList
     *
     * @param idx
     * @return AccountResponseDto
     */
    List<AccountResponseDto> getAccountListInProject(Long idx);

    /**
     * account 권한 검사
     */
    void checkAccountPermission(List<AccountResponseDto> accountList, Long accountIdx);

    /**
     * project의 join한 유저 목록
     * @param project
     * @return
     */
    List<AccountResponseDto> joinedProject(Project project);
}
