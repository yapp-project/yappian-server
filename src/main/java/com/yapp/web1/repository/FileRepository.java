package com.yapp.web1.repository;

import com.yapp.web1.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository  extends JpaRepository<File,Long> {
    void deleteByProjectIdx(Long projectIdx);
    List<File> findByProjectIdx(Long projectIdx);
}
