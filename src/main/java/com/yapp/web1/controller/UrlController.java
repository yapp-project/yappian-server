package com.yapp.web1.controller;

import com.yapp.web1.dto.req.UrlRequestDto;
import com.yapp.web1.dto.res.ApiResponse;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UrlResponseDto;
import com.yapp.web1.exception.Common.NoPermissionException;
import com.yapp.web1.exception.Common.NotFoundException;
import com.yapp.web1.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/v1/api")
@RestController
public class UrlController {

    private final UrlService urlService;

    /**
     * 해당 프로젝트의 URL 불러오기
     * @param projectIdx
     * @param session 로그인 유저 정보
     * @return 해당 프로젝트의 url List
     * @throws Exception join한 유저만 url 생성 가능
     * @see /v1/api/project/{projectIdx}/url/list
     */
    @GetMapping("/project/{projectIdx}/url/list")
    public ApiResponse<?> getUrl(@PathVariable Long projectIdx, HttpSession session) {
        List<UrlResponseDto> urlResponseDto = null;
        try {
            urlResponseDto = urlService.getUrl(projectIdx);
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED)
                    .message("Url 가져오기 성공")
                    .data(urlResponseDto)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("해당 프로젝트 없음")
                    .build();
        }

    }

    /**
     * URL 생성
     * @param projectIdx
     * @param url     생성할 Url 관련 데이터
     * @param session 로그인 유저 정보
     * @return  해당 프로젝트정보와 url List
     * @throws Exception join한 유저만 url 생성 가능
     * @see /v1/api/project/{projectIdx}/url
     */
    @PostMapping("project/{projectIdx}/url")
    public ApiResponse<?> createUrl(@PathVariable Long projectIdx, @RequestBody UrlRequestDto url, HttpSession session) {
        ProjectResponseDto projectResponseDto = null;
        try {
            projectResponseDto = urlService.createUrl(projectIdx, url, 1L);// 1 : dummy data
            return ApiResponse.builder()
                              .status(HttpStatus.CREATED)
                              .message("Url 생성 성공")
                              .data(projectResponseDto)
                              .build();
        } catch (NoPermissionException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .build();
        }

    }

    /**
     * Url 삭제
     * @param idx - urlIdx
     * @param session
     * @see /v1/api/project/{projectIdx}/url/{idx}
     */

    @DeleteMapping("project/{projectIdx}/url/{idx}")
    public ApiResponse deleteUrl(@PathVariable final Long projectIdx,@PathVariable final Long idx, HttpSession session) {
        try {
            urlService.deleteUrl(projectIdx, idx, 1L);// 1L : dummy data
            return ApiResponse.builder()
                    .status(HttpStatus.NO_CONTENT)
                    .message("Url 삭제 성공")
                    .build();
        } catch (NotFoundException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(e.getMessage())
                    .build();
        } catch (NoPermissionException e) {
            return ApiResponse.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .build();
        }

    }
}
