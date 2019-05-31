package com.yapp.web1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.security.Principal;

/**
 * Auth Controller
 * 로그인 성공 테스트용 컨트롤러
 *
 * @author Dakyung Ko
 * @since 0.0.4
 * @version 1.0
 */
@Api(tags = "인증체크 API")
@RestController
public class AuthController {

    @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessions;

    @ApiOperation(value = "로그인 유저 데이터 확인")
    @GetMapping("/auth")
    public Principal home(Principal principal) {
        return principal;
    }

    @ApiOperation(value = "로그인 유저 세션 확인")
    @GetMapping("/session")
    public String currentSession(){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser")
            return "ANONYMOUS";

        Session findSession = sessions.getSession(RequestContextHolder.currentRequestAttributes().getSessionId());
        if(findSession != null)
            return findSession.getId();

        return "invalid";
    }
}
