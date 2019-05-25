package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Account;
import com.yapp.web1.domain.File;
import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.dto.res.AccountResponseDto;
import com.yapp.web1.exception.Common.NoPermissionException;
import com.yapp.web1.exception.Common.NotFoundException;
import com.yapp.web1.repository.FileRepository;
import com.yapp.web1.repository.OrdersRepository;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.repository.AccountRepository;
import com.yapp.web1.service.CommonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final ProjectRepository projectRepository;
    private final OrdersRepository ordersRepository;
    private final AccountRepository accountRepository;
    private final FileRepository fileRepository;

    // File findByFileId
    @Transactional(readOnly = true)
    @Override
    public File findFileById(Long idx){
        return fileRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 파일 없음"));
    }

    // Orders findByOrdersId
    @Transactional(readOnly = true)
    @Override
    public Orders findOrdersById(Long idx){
        return ordersRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 기수 없음"));
    }

    // Account findByAccountId
    @Transactional(readOnly = true)
    @Override
    public Account findAccountById(Long idx){
        return accountRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 유저 없음"));
    }

    // project findById
    @Transactional(readOnly = true)
    @Override
    public Project findById(Long idx) {
        return projectRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 프로젝트 없음"));
    }

    // accountList
    @Transactional(readOnly = true)
    @Override
    public List<AccountResponseDto> getAccountListInProject(Long idx) {

        Project project = findById(idx);
        Set<Account> accountSet = project.getAccountList();

        List<AccountResponseDto> accountList = new ArrayList<>();

        Account account;
        Iterator<Account> it = accountSet.iterator();

        while (it.hasNext()) {
            account = it.next();
            accountList.add(new AccountResponseDto(account.getIdx(), account.getName()));
        }
        return accountList;
    }

    // account 권한 검사
    @Transactional(readOnly = true)
    @Override
    public void checkAccountPermission(List<AccountResponseDto> accountList, Long accountIdx) {
        boolean check = false;
        for (AccountResponseDto account : accountList) {
            if ((account.getAccountIdx()).equals((accountIdx))) {
                check = true;
            }
        }
        if (!check) {
            throw new NoPermissionException("이 유저는 권한이 없습니다.");
        }
    }

    // project의 join한 유저 목록 res dto
    @Override
    public List<AccountResponseDto> joinedProject(Project project){
        Set<Account> accountSet = project.getAccountList();
        Account u;

        List<AccountResponseDto> accountResponseDtos =new ArrayList<>();

        Iterator<Account> it = accountSet.iterator();

        while(it.hasNext()){
            u = it.next();
            accountResponseDtos.add(new AccountResponseDto(u.getIdx(),u.getName()));
        }
        return accountResponseDtos;
    }


}
