package com.yapp.web1.service;

import com.yapp.web1.domain.Project;
import com.yapp.web1.dto.res.UserResponseDto;

import java.util.List;

public interface CommonService {

    Project findById(Long idx);
    List<UserResponseDto> getUserListInProject(Long idx);
    void checkUserPermission(List<UserResponseDto> userList, Long userIdx);
    List<UserResponseDto> joinedProject(Project project);
}
