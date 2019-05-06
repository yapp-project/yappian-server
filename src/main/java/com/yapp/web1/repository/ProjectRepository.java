package com.yapp.web1.repository;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByOrdersIdxAndFinalCheck(Long orderIdx, Mark finalCheck);
    Project findByName(String name);
}
