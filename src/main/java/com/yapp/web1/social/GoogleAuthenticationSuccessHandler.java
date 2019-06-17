package com.yapp.web1.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.web1.common.SessionConstants;
import com.yapp.web1.domain.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class GoogleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private HttpSession httpSession;
    private ObjectMapper objectMapper;

    public GoogleAuthenticationSuccessHandler(HttpSession httpSession, ObjectMapper objectMapper) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        httpSession.setAttribute(SessionConstants.LOGIN_USER, getGoogleUser(authentication));
        response.sendRedirect("/");
    }

    private Account getGoogleUser(Authentication authentication) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        return objectMapper.convertValue(oAuth2Authentication.getUserAuthentication().getDetails(), Account.class);
    }
}