package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.User;
import com.yapp.web1.dto.res.ProjectListinUserResDto;
import com.yapp.web1.repository.UserRepository;
import com.yapp.web1.service.CommonService;
import com.yapp.web1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * UserService 구현 클래스
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.0
 */
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommonService commonService;

    // user'projectList
    @Override
    public List<ProjectListinUserResDto> getProjectList(Long userIdx) {
        User user = commonService.findUserById(userIdx);

        Set<Project> projectSet = user.getJoinedProjects();

        List<Project> setToList = new ArrayList(projectSet);

        List<ProjectListinUserResDto> projectListinUserResDtos = new ArrayList<>();

        for(Project project : setToList){
            ProjectListinUserResDto dto = ProjectListinUserResDto.builder()
                    .idx(project.getIdx())
                    .projectType(project.getType())
                    .orderNumber(project.getOrders().getNumber())
                    .projectName(project.getName())
                    .build();
            projectListinUserResDtos.add(dto);
        }

        Collections.sort(projectListinUserResDtos);

        return projectListinUserResDtos;
    }

    // 로그인 구현 후 삭제
    private User testUser(){
        User findUser = userRepository.findByName("테스트유저");
        if(findUser != null) return findUser;
        else return userRepository.save(User.builder().name("테스트유저").email("test@test.com").build());
    }

    // 로그인 구현 후 삭제
    @Override
    public User getCurrentUser(){
        return testUser();
    }

    @Override
    public User findByIdx(Long idx) {
        return null;
    }
}
