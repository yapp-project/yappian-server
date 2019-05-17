package com.yapp.web1.social;

import com.yapp.web1.domain.User;
import com.yapp.web1.service.UserService;
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

    private final UserService userService;

    public UsernamePasswordAuthenticationToken doAuthentication(UserConnection userConnection) {
        if(userService.isExistUser(userConnection)) {
            final User user = userService.findBySocial(userConnection);
            return setAuthenticationToken(user);
        } else {
            final User user = userService.signUp(userConnection);
            return setAuthenticationToken(user);
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
