package com.yapp.web1.common;

import com.yapp.web1.domain.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        return account;
    }
}
