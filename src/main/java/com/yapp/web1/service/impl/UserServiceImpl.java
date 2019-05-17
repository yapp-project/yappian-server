package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.User;
import com.yapp.web1.dto.res.ProjectListinUserResDto;
import com.yapp.web1.repository.UserRepository;
import com.yapp.web1.service.CommonService;
import com.yapp.web1.service.UserService;
import com.yapp.web1.social.UserConnection;
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
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommonService commonService;

    // user'projectList
    @Override
    @Transactional(readOnly = true)
    public List<ProjectListinUserResDto> getProjectList(Long userIdx) {
        User user = commonService.findUserById(userIdx);

        Set<Project> projectSet = user.getJoinedProjects();

        List<Project> setToList = new ArrayList<>(projectSet);

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

    @Override
    @Transactional
    public User signUp(UserConnection userConnection) {
        final User user = User.signUp(userConnection);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User findBySocial(UserConnection userConnection) {
        final User user = userRepository.findBySocial(userConnection);
        if (user == null) throw new RuntimeException();
        return user;
    }

    @Override
    @Transactional
    public boolean isExistUser(UserConnection userConnection) {
        final User user = userRepository.findBySocial(userConnection);
        return (user != null ? true : false);
    }

    // TODO 로그인 구현 후 삭제
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
