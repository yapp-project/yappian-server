package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Comment;
import com.yapp.web1.domain.Task;
import com.yapp.web1.domain.User;
import com.yapp.web1.dto.req.TaskRequestDto;
import com.yapp.web1.dto.res.CommentResponseDto;
import com.yapp.web1.dto.res.NoticeListResponseDto;
import com.yapp.web1.dto.res.TaskResponseDto;
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.repository.TaskRepository;
import com.yapp.web1.service.TaskService;
import com.yapp.web1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * TaskService 구현 클래스
 *
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.0
 */
@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public TaskResponseDto createTask(TaskRequestDto dto, User user) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public TaskResponseDto getTask(Long idx) {
        Task findTask = taskRepository.findById(idx)
                .orElseThrow(()->new EntityNotFoundException("해당 Task 없음"));

        List<User> works = findTask.getWorks();
        List<Comment> commentList = findTask.getCommentList();

        TaskResponseDto dto = TaskResponseDto.builder()
                .taskTitle(findTask.getTitle())
                .taskStatus(findTask.getStatus())
                .taskJob(findTask.getJob())
                .userList(getUserResponseDtoList(works)) // 담당자 유저 목록 조회
                .startDate(findTask.getStartDate())
                .endDate(findTask.getEndDate())
                .contents(findTask.getContents())
                .comments(getCommentResponseDtoList(commentList)) // comment 목록
                .build();
        return dto;
    }

    /**
     * User List를 UserResponseDto List로 변환하는 함수
     *
     * @param userList Dto로 변환할 User List
     * @return 변환한 UserResponseDto List
     */
    private List<UserResponseDto> getUserResponseDtoList(List<User> userList){
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for(User user : userList) {
            UserResponseDto dto = UserResponseDto.builder().userIdx(user.getIdx()).name(user.getName()).build();
            userResponseDtoList.add(dto);
        }
        return userResponseDtoList;
    }

    /**
     * Comment List를 CommentResponseDto List로 변환하는 함수
     *
     * @param commentList Dto로 변환할 Comment List
     * @return 변환한 CommentResponseDto List
     */
    private List<CommentResponseDto> getCommentResponseDtoList(List<Comment> commentList){
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment comment : commentList){
            User user = comment.getUser();

            CommentResponseDto dto = CommentResponseDto.builder()
                    .user(getUserResponseDto(user))
                    .contents(comment.getContents()).build();
            commentResponseDtoList.add(dto);
        }
        return commentResponseDtoList;
    }

    /**
     * User를 UserResponseDto로 변환하는 함수
     *
     * @param user Dto로 변환할 User
     * @return 변환한 UserResponseDto
     */
    private UserResponseDto getUserResponseDto(User user){
        return UserResponseDto.builder().userIdx(user.getIdx()).name(user.getName()).build();
    }

    @Override
    public TaskResponseDto editTask(Long idx, TaskRequestDto dto, User user) {
        return null;
    }

    @Transactional
    @Override
    public boolean deleteTask(Long idx, User user) {
        // Task idx check
        Task findTask = taskRepository.findById(idx)
                .orElseThrow(()->new EntityNotFoundException("해당 Task 없음"));

        // 임시 로그인 User 데이터
        user = userService.getCurrentUser();

        // 로그인 User 데이터와 프로젝트에 Join한 User 데이터 check
        Set<User> joinedUsers = findTask.getProject().getUserList();
        System.out.println(joinedUsers.size());
        if(!joinedUsers.contains(user)) return false; // 추후 Exception 처리

        // 연관관계 삭제
        findTask.getReadList().clear();
        findTask.getWorks().clear();
        findTask.getCommentList().clear();

        taskRepository.deleteById(idx);
        return true;
    }

    @Override
    public List<NoticeListResponseDto> getNotice(User user) {
        return null;
    }
}
