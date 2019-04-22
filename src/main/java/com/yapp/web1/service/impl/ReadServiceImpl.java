package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Url;
import com.yapp.web1.domain.User;
import com.yapp.web1.repository.ReadRepository;
import com.yapp.web1.repository.TaskRepository;
import com.yapp.web1.service.ReadService;
import com.yapp.web1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * ReadService 구현 클래스
 *
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.0
 */
@Service
@Transactional
@AllArgsConstructor
public class ReadServiceImpl implements ReadService {

    private final ReadRepository readRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public boolean readCheck(Long idx, User user) {
        user = userService.getCurrentUser();
        Url url = taskRepository.findById(idx).orElseThrow(()->new EntityNotFoundException("해당 Url 없음"));
        Read read = Read.builder().task(url).user(user).build();
        readRepository.save(read);
        return true;
    }
}
