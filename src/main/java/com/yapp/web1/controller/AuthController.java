package com.yapp.web1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Auth Controller
 * 로그인 성공 테스트용 컨트롤러
 *
 * @author Dakyung Ko
 * @since 0.0.4
 * @version 1.0
 */
@RestController
public class AuthController {

    @GetMapping("/auth")
    public Principal home(Principal principal) {
        return principal;
    }

}
