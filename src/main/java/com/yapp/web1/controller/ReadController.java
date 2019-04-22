package com.yapp.web1.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Read Controller
 *
 * @author Dakyung Ko
 * @since 0.0.3
 * @version 1.0
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class ReadController {
    /**
     * Task 읽음 처리
     *
     * @param idx 읽음 처리할 Task idx
     * @param session 로그인 User session
     *
     * @see /v1/api/read/{idx}
     */
    @PostMapping("/read/{idx}")
    public ResponseEntity readCheck(@PathVariable Long idx, HttpSession session){
        return new ResponseEntity(HttpStatus.OK);
    }
}
