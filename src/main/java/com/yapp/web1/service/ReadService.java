package com.yapp.web1.service;

import com.yapp.web1.domain.User;

/**
 * ReadService Interface
 * Url 읽음 관련 기능
 *
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.0
 */
public interface ReadService {
    /**
     * Url 읽음 처리
     *
     * @param idx 읽음 처리할 Url idx
     * @param user 로그인 유저
     */
    boolean readCheck(Long idx, User user);
}
