package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.User;
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.exception.Common.NoPermissionException;
import com.yapp.web1.exception.Common.NotFoundException;
import com.yapp.web1.repository.OrdersRepository;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.repository.UserRepository;
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
    private final UserRepository userRepository;

    // Orders findByOrdersId
    @Transactional(readOnly = true)
    @Override
    public Orders findOrdersById(Long idx){
        return ordersRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 기수 없음"));
    }

    // User findByUserId
    @Transactional(readOnly = true)
    @Override
    public User findUserById(Long idx){
        return userRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 유저 없음"));
    }

    // project findById
    @Transactional(readOnly = true)
    @Override
    public Project findById(Long idx) {
        return projectRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 프로젝트 없음"));
    }

    // userList
    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> getUserListInProject(Long idx) {

        Project project = findById(idx);
        Set<User> userSet = project.getUserList();

        List<UserResponseDto> userList = new ArrayList<>();

        User user;
        Iterator<User> it = userSet.iterator();

        while (it.hasNext()) {
            user = it.next();
            userList.add(new UserResponseDto(user.getIdx(), user.getName()));
        }
        return userList;
    }

    // user 권한 검사
    @Transactional(readOnly = true)
    @Override
    public void checkUserPermission(List<UserResponseDto> userList, Long userIdx) {
        boolean check = false;
        for (UserResponseDto user : userList) {
            if ((user.getUserIdx()).equals((userIdx))) {
                check = true;
            }
        }
        if (!check) {
            throw new NoPermissionException("이 유저는 권한이 없습니다.");
        }
    }

    // joinedProject
    @Override
    public List<UserResponseDto> joinedProject(Project project){
        Set<User> userSet = project.getUserList();
        User u;

        List<UserResponseDto> userResponseDtos =new ArrayList<>();

        Iterator<User> it = userSet.iterator();

        while(it.hasNext()){
            u = it.next();
            userResponseDtos.add(new UserResponseDto(u.getIdx(),u.getName()));
        }
        return userResponseDtos;
    }


}
