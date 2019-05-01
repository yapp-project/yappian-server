package com.yapp.web1.service;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.User;
import com.yapp.web1.dto.res.UserResponseDto;

import java.util.List;

public interface CommonService {

    /**
     * 해당 엔티티 있는지 확인
     * @param idx
     * @return Project, Orders, User
     */
    Project findById(Long idx);
    Orders findOrdersById(Long idx);
    User findUserById(Long idx);

    /**
     * 해당 프로젝트에 join된 userList
     *
     * @param idx
     * @return UserResponseDto
     */
    List<UserResponseDto> getUserListInProject(Long idx);

    /**
     * user 권한 검사
     */
    void checkUserPermission(List<UserResponseDto> userList, Long userIdx);
    List<UserResponseDto> joinedProject(Project project);
}
