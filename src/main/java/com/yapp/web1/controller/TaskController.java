package com.yapp.web1.controller;

import com.yapp.web1.domain.Task;
import com.yapp.web1.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Task Controller
 *
 * @author Dakyung Ko
 * @since 0.0.2
 * @version 1.0
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class TaskController {

//    private final TaskService taskService;

    /**
     * 소속 중인 프로젝트의 읽지 않은 새로운 Task 목록
     *
     * @param user 알림을 읽어올 유저 데이터
     *
     * @see /v1/api/notice/tasks
     */
    @GetMapping("/notice/tasks")
    public ResponseEntity<List<Task>> getNotice(@RequestBody User user){
        List<Task> taskList = new ArrayList<>();
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

}
