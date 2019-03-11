package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Project;
import com.yapp.web1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @Transactional(readOnly = true)
    @Override
    public List<Project> getFavoriteProjects(HttpSession session) {
        return null;
    }

    @Override
    public boolean setFavoriteProject(Long idx, HttpSession session) {
        return false;
    }

    @Override
    public Project joinProject(Long idx, HttpSession session) {
        return null;
    }
}
