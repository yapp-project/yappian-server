package com.yapp.web1.service;

import com.yapp.web1.domain.User;
import com.yapp.web1.dto.req.TaskRequestDto;
import com.yapp.web1.dto.res.NoticeListResponseDto;
import com.yapp.web1.dto.res.TaskResponseDto;

import java.util.List;

/**
 * TaskService Interface
 *
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.0
 */
public interface TaskService {
    /**
     * 테스크 생성
     *
     * @param dto 생성할 Url 정보
     * @param user 로그인 유저 정보
     * @return 생성한 Url 정보
     *
     * @exception Exception 로그인 User가 조회한 joined 테이블의 유저 속하지 않으면
     */
    TaskResponseDto createTask(TaskRequestDto dto, User user);

    /**
     * 테스크 상세
     *
     * @param idx 조회할 Url idx
     * @return 조회한 Url 정보
     */
    TaskResponseDto getTask(Long idx);

    /**
     * 테스크 수정
     *
     * @param idx 수정할 Url idx
     * @param dto 수정할 Url 정보
     * @param user 로그인 유저 정보
     *
     * @exception Exception 로그인 User가 조회한 joined 테이블의 유저 속하지 않으면
     */
    TaskResponseDto editTask(Long idx, TaskRequestDto dto, User user);

    /**
     * 테스크 삭제
     *
     * @param idx 삭제할 Url idx
     * @param user 로그인 유저 정보
     *
     * @exception Exception 로그인 User가 조회한 joined 테이블의 유저 속하지 않으면
     */
    boolean deleteTask(Long idx, User user);

    /**
     * 테스크 알림 목록
     *
     * @param user 로그인 유저 정보
     */
    List<NoticeListResponseDto> getNotice(User user);
}
