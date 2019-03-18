package com.yapp.web1.controller;

import com.yapp.web1.dto.res.ProjectListResponseDto;
import com.yapp.web1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User Controller
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.3
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class UserController {

    private UserService userService;


    /**
     * 즐겨찾는 프로젝트 목록 반환
     *
     * @param session 로그인 유저 session
     * @return 즐겨찾는 프로젝트 list
     *
     * @see /v1/api/favorites
     */
    @GetMapping("/favorites")
    public ResponseEntity<List<ProjectListResponseDto>> getFavoriteProjects(HttpSession session){
        List<ProjectListResponseDto> projectList = userService.getFavoriteProjects(null);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    /**
     * 프로젝트 즐겨찾기 설정 및 해제
     *
     * @param idx 즐겨찾기 설정할 프로젝트 idx
     * @param session 로그인 유저 session
     * @exception Exception 추후 수정
     *
     * @see /v1/api/favorite/{idx}
     */
    @PutMapping("/favorite/{idx}")
    public ResponseEntity setFavoriteProject(@PathVariable final Long idx, HttpSession session){
        userService.setFavoriteProject(idx, null);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 프로젝트 참여 및 참여 취소(나가기)
     *
     * @param idx 참여할 프로젝트 idx
     * @param session 로그인 유저 session
     * @exception Exception 이미 join된 유저 - 추후 수정
     *
     * @see /v1/api/join/{idx}
     */
    @PutMapping("/join/{idx}")
    public ResponseEntity joinProject(@PathVariable final Long idx, HttpSession session){
        userService.joinProject(idx, null);
        return new ResponseEntity(HttpStatus.OK);
    }

}
