package com.yapp.web1.repository;

import com.yapp.web1.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByProjectIdx(Long idx);
}
