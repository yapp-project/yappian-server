package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.Url;
import com.yapp.web1.domain.User;
import com.yapp.web1.dto.req.TaskRequestDto;
import com.yapp.web1.dto.res.CommentResponseDto;
import com.yapp.web1.dto.res.NoticeListResponseDto;
import com.yapp.web1.dto.res.TaskResponseDto;
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.repository.TaskRepository;
import com.yapp.web1.service.ReadService;
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
    private final ProjectRepository projectRepository;
    private final ReadService readService;

    /**
     * 찾는 Url 있는지 check
     *
     * @param idx 찾으려는 Url idx
     * @return 찾은 Url
     * @exception EntityNotFoundException 찾는 Task가 없는 경우
     */
    private Url findByIdx(Long idx){
        Url findUrl = taskRepository.findById(idx)
                .orElseThrow(()->new EntityNotFoundException("해당 Url 없음"));
        return findUrl;
    }

    @Transactional
    @Override
    public TaskResponseDto createTask(TaskRequestDto dto, User user) {
        // 임시 로그인 User 데이터
        user = userService.getCurrentUser();

        // 프로젝트 찾기
        Project project = projectRepository.findById(dto.getProjectIdx())
                            .orElseThrow(()->new EntityNotFoundException("해당 Project 없음"));
        // 연관된 프로젝트 check
        Set<Project> projects = user.getJoinedProjects();
        if(!projects.contains(project)) return null; // Exception

        // Url 저장
        Url url = dto.toEntity(project);
        url.getWorks().add(user);
        //        url.getFileList().add();
        Url savedUrl = taskRepository.save(url);

        // 생성한 Url 읽음 처리
        readService.readCheck(savedUrl.getIdx(), user);

        // dto에서 User Idx 목록
        List<Long> userIdxList = dto.getUserList();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        // RequestDto를 ResponsDto로 변환
        return TaskResponseDto.builder()
                .taskTitle(dto.getTitle())
                .taskStatus(dto.getUrlType())
                .taskJob(dto.getTaskJob())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .contents(dto.getContents())
//                .userList(userIdxToUser(userIdxList))// UserResponseDto List
                .comments(commentResponseDtoList) // 빈 commentResponseDtoList
                .build();
    }

    /**
     * UserIdx List를 UserResponseDto List로 변환
     *
     * @param userIdxList
     * @return 변환한 UserResponseDto List
     */
    private List<UserResponseDto> userIdxToUser(List<Long> userIdxList){
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for(Long idx : userIdxList){
            User findUser = userService.findByIdx(idx);
            UserResponseDto dto = UserResponseDto.builder().userIdx(idx).name(findUser.getName()).build();
            userResponseDtoList.add(dto);
        }
        return userResponseDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public TaskResponseDto getTask(Long idx) {
        Url findUrl = findByIdx(idx);

        List<User> works = findUrl.getWorks();
        List<Comment> commentList = findUrl.getCommentList();

        TaskResponseDto dto = TaskResponseDto.builder()
                .taskTitle(findUrl.getTitle())
                .taskStatus(findUrl.getType())
                .taskJob(findUrl.getJob())
                .userList(getUserResponseDtoList(works)) // 담당자 유저 목록 조회
                .startDate(findUrl.getStartDate())
                .endDate(findUrl.getEndDate())
                .contents(findUrl.getContents())
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

    /**
     * Task를 통해 User가 Project에 속해(join)있는지 check
     *
     * @param findUrl joined table 검색할 task 정보
     * @param currentUser project에 join되어있는지 확인할 User 정보
     *
     * @return check 여부
     */
    private boolean joinProjectCheckByTask(Url findUrl, User currentUser){
        Set<User> joinedUsers = findUrl.getProject().getUserList();
        System.out.println(joinedUsers.size());
        if(!joinedUsers.contains(currentUser)) return false; // 추후 Exception 처리
        return true;
    }

    @Transactional
    @Override
    public boolean deleteTask(Long idx, User user) {
        // Url idx check
        Url findUrl = findByIdx(idx);

        // 임시 로그인 User 데이터
        user = userService.getCurrentUser();

        // 로그인 User 데이터와 프로젝트에 Join한 User 데이터 check
        if(!joinProjectCheckByTask(findUrl, user)) return false;

        // 연관관계 삭제
        findUrl.getReadList().clear();
        findUrl.getWorks().clear();
        findUrl.getCommentList().clear();

        taskRepository.deleteById(idx);
        return true;
    }

    @Override
    public List<NoticeListResponseDto> getNotice(User user) {
        return null;
    }
}
