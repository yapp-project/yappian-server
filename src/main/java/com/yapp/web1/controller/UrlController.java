package com.yapp.web1.controller;

import com.yapp.web1.dto.req.UrlRequestDto;
import com.yapp.web1.dto.res.ApiResponse;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.exception.Url.NoPermissionException;
import com.yapp.web1.exception.Url.NotFoundException;
import com.yapp.web1.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class UrlController {

    private final UrlService urlService;

    /**
     * URL 생성
     *
     * @param url     생성할 Url 관련 데이터 - projectId 포함(UrlRequestDto)
     * @param session 로그인 유저 정보
     * @return 생성한 url 데이터(UrlResponseDto), 해당 프로젝트의 url List
     * @throws Exception join한 유저만 url 생성 가능
     * @see /v1/api/url
     */
    @PostMapping("/url")
    public ApiResponse<?> createUrl(@RequestBody UrlRequestDto url, HttpSession session) {
        ProjectResponseDto projectResponseDto = null;
        try {
            projectResponseDto = urlService.createUrl(url, 201632025L);// 201632025L : dummy data
            return ApiResponse.builder()
                              .status(HttpStatus.CREATED)
                              .message("Url 생성 성공")
                              .data(projectResponseDto)
                              .build();
        } catch (NoPermissionException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message("유저 권한 없음")
                    .build();
        }

    }

    /**
     * Url 삭제
     * @param idx - urlIdx
     * @param session
     * @return
     */
    @Transactional
    @DeleteMapping("/url/{idx}")
    public ApiResponse deleteUrl(@PathVariable final Long idx, HttpSession session) {
        try {
            urlService.deleteUrl(idx, 201632025L);// 201632025L : dummy data
            return ApiResponse.builder()
                    .status(HttpStatus.NO_CONTENT)
                    .message("Url 삭제 성공")
                    .build();
        } catch (NotFoundException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("삭제 할 리소스 없음")
                    .build();
        } catch (NoPermissionException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message("유저 권한 없음")
                    .build();
        }

    }
}
