package com.yapp.web1.social;

import com.yapp.web1.domain.Account;
import com.yapp.web1.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SocialService
 *
 * @author Dakyung Ko
 */
@Component
@AllArgsConstructor
public class SocialService {

    private final AccountService accountService;

    public UsernamePasswordAuthenticationToken doAuthentication(AccountConnection accountConnection) {
        if(accountService.isExistUser(accountConnection)) {
            final Account account = accountService.findBySocial(accountConnection);
            return setAuthenticationToken(account);
        } else {
            final Account account = accountService.signUp(accountConnection);
            return setAuthenticationToken(account);
        }
    }

    private UsernamePasswordAuthenticationToken setAuthenticationToken(Object user) {
        return new UsernamePasswordAuthenticationToken(user, null, getAuthorities("ROLE_USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
