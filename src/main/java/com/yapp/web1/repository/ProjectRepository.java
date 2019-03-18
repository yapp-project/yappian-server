package com.yapp.web1.repository;

import com.yapp.web1.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    public List<Project> findAllByOrdersIdx(Long orderIdx);
}
