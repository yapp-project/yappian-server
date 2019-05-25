package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Account;
import com.yapp.web1.domain.Project;
import com.yapp.web1.dto.res.ProjectListInAccountResDto;
import com.yapp.web1.repository.AccountRepository;
import com.yapp.web1.service.AccountService;
import com.yapp.web1.service.CommonService;
import com.yapp.web1.social.AccountConnection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * AccountService 구현 클래스
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CommonService commonService;

    // account'projectList
    @Override
    @Transactional(readOnly = true)
    public List<ProjectListInAccountResDto> getProjectList(Long accountIdx) {
        Account account = commonService.findAccountById(accountIdx);

        Set<Project> projectSet = account.getJoinedProjects();

        List<Project> setToList = new ArrayList<>(projectSet);

        List<ProjectListInAccountResDto> projectListInAccountResDtos = new ArrayList<>();

        for(Project project : setToList){
            ProjectListInAccountResDto dto = ProjectListInAccountResDto.builder()
                    .idx(project.getIdx())
                    .projectType(project.getType())
                    .orderNumber(project.getOrders().getNumber())
                    .projectName(project.getName())
                    .build();
            projectListInAccountResDtos.add(dto);
        }

        Collections.sort(projectListInAccountResDtos);

        return projectListInAccountResDtos;
    }

    @Override
    @Transactional
    public Account signUp(AccountConnection accountConnection) {
        final Account account = Account.signUp(accountConnection);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account findBySocial(AccountConnection accountConnection) {
        final Account account = accountRepository.findBySocial(accountConnection);
        if (account == null) throw new RuntimeException();
        return account;
    }

    @Override
    @Transactional
    public boolean isExistUser(AccountConnection accountConnection) {
        final Account account = accountRepository.findBySocial(accountConnection);
        return (account != null ? true : false);
    }
}
