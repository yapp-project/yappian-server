package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Project;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * ProjectService 구현 클래스
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.0
 */
@Service
@Transactional
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project createProject(Project project, HttpSession session) {
        return null;
    }

    @Override
    public boolean deleteProject(Long idx, HttpSession session) {
        return false;
    }

    @Override
    public boolean updateProject(Long idx, Project project) {
        return false;
    }
}
