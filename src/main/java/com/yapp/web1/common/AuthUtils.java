package com.yapp.web1.common;

import com.yapp.web1.domain.Account;
import com.yapp.web1.exception.NoPermissionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * AuthUtils
 * 현재 사용자 조회
 *
 * @author Dakyung Ko
 */
public class AuthUtils {

    public static Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new NoPermissionException("로그인한 사용자가 없습니다.");
        return (Account) authentication.getPrincipal();
    }
}
