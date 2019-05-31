package com.yapp.web1.controller;

import com.yapp.web1.common.AuthUtils;
import com.yapp.web1.dto.res.ProjectListInAccountResDto;
import com.yapp.web1.exception.NoPermissionException;
import com.yapp.web1.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Account Controller
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.4
 * @version 1.4
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api")
@RestController
@Api(tags = "유저(Account) APIs")
public class AccountController {

    private AccountService accountService;

    /**
     * 내가 조인한 프로젝트 목록 조회
     *
     * @see /api/user/projects
     */
    @GetMapping("user/projects")
    @ApiOperation(value = "내가 조인한 프로젝트 목록 조회(인증 필요)")
    public ResponseEntity<?> getProjectList() {
        try {
            List<ProjectListInAccountResDto> projectList = accountService.getProjectList(AuthUtils.getCurrentAccount().getIdx());
            return new ResponseEntity<>(projectList, HttpStatus.OK);
        } catch (NoPermissionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
