package com.yapp.web1.controller;

import com.yapp.web1.dto.res.ProjectListinUserResDto;
import com.yapp.web1.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User Controller
 *
 * @author Jihye Kim
 * @since 0.0.4
 * @version 1.4
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
@Api(tags = "유저 APIs")
public class UserController {

    private UserService userService;

    /**
     * 내가 조인한 프로젝트 목록 조회
     *
     * @see /v1/api/user/projects
     */
    @GetMapping("user/projects")
    @ApiOperation(value = "내가 조인한 프로젝트 목록 조회(인증 필요 없음)")
    public ResponseEntity<?> getProjectList(@ApiIgnore HttpSession session) {
        List<ProjectListinUserResDto> projectList = userService.getProjectList(1L);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }
}
