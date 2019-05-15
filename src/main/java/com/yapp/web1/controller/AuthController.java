package com.yapp.web1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {

    @GetMapping("/")
    public Principal home(Principal principal) {
        return principal;
    }

}
