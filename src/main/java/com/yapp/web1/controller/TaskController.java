package com.yapp.web1.controller;

import com.yapp.web1.dto.req.TaskRequestDto;
import com.yapp.web1.dto.res.NoticeListResponseDto;
import com.yapp.web1.dto.res.TaskResponseDto;
import com.yapp.web1.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Task Controller
 *
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.1
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class TaskController {

    private final TaskService taskService;

    /**
     * 소속 중인 프로젝트의 읽지 않은 새로운 Task 목록
     *
     * @param session 알림을 읽어올 로그인 유저 데이터
     * @return 유저가 참여한 프로젝트의 읽지 않은 Task List
     *
     * @see /v1/api/notice/tasks
     */
    @GetMapping("/notice/tasks")
    public ResponseEntity<List<NoticeListResponseDto>> getNotice(HttpSession session){
        List<NoticeListResponseDto> taskList = new ArrayList<>();
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    /**
     * 해당 프로젝트의 모든 task 목록
     *
     * @param idx 프로젝트 idx
     * @return Task 목록
     *
     * @see /v1/api/project/{idx}/tasks
     *//*
    @GetMapping("/project/{idx}/tasks") // API 디자인
    public ResponseEntity<List<TaskListResponseDto>> getTaskList(@PathVariable final Long idx){
        List<TaskListResponseDto> taskList = new ArrayList<>();
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }*/ // 현재는 getProject 기능과 중복. 추후 필요하면 추가

    /**
     * 테스크 생성
     *
     * @param task 생성할 task 정보
     * @param session 로그인 유저 정보
     * @return 생성한 Task 정보
     *
     * @see /v1/api/task
     */
    @PostMapping("/task")
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody final TaskRequestDto task, HttpSession session){
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    /**
     * 테스크 상세
     *
     * @param idx 조회할 테스크 idx
     * @return 조회한 Task 정보
     *
     * @see /v1/api/task/{idx}
     */
    @GetMapping("/task/{idx}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable final Long idx){
        TaskResponseDto dto = taskService.getTask(idx);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * 테스크 수정
     *
     * @param idx 수정할 테스크 idx
     * @param task 테스크 수정 정보
     * @param session 로그인 유저 정보
     * @return 수정한 Task 정보
     *
     * @exception Exception User idx 불일치시 - 추후 수정
     *
     * @see /v1/api/task/{idx}
     */
    @PutMapping("/task/{idx}")
    public ResponseEntity<TaskResponseDto> editTask(@PathVariable final Long idx, @Valid @RequestBody final TaskRequestDto task, HttpSession session) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * 테스크 삭제
     *
     * @param idx 삭제할 테스크 idx
     * @param session 로그인 유저 정보
     * @return 삭제 성공 여부
     *
     * @exception Exception User idx 불일치시 - 추후 수정
     *
     * @see /v1/api/task/{idx}
     */
    @DeleteMapping("/task/{idx}")
    public ResponseEntity deleteTask(@PathVariable final Long idx, HttpSession session) {
        if(!taskService.deleteTask(idx, null)) return new ResponseEntity(HttpStatus.NOT_FOUND); //  return false 시 Exception
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
