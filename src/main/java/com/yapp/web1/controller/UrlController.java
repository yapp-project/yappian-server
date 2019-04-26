package com.yapp.web1.controller;

import com.yapp.web1.dto.req.UrlRequestDto;
import com.yapp.web1.dto.res.ApiResponse;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UrlResponseDto;
import com.yapp.web1.exception.Url.NoPermissionException;
import com.yapp.web1.exception.Url.NotFoundException;
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
     * @param idx projectIdx
     * @param session 로그인 유저 정보
     * @return 해당 프로젝트의 url List
     * @throws Exception join한 유저만 url 생성 가능
     * @see /v1/api/{idx}/url
     */
    @GetMapping("{idx}/url")
    public ApiResponse<?> getUrl(@PathVariable Long idx, HttpSession session) {
        List<UrlResponseDto> urlResponseDto = null;
        try {
            urlResponseDto = urlService.getUrl(idx);
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
     * @param idx projectIdx
     * @param url     생성할 Url 관련 데이터
     * @param session 로그인 유저 정보
     * @return  해당 프로젝트정보와 url List
     * @throws Exception join한 유저만 url 생성 가능
     * @see /v1/api/{idx}/url
     */
    @PostMapping("{idx}/url")
    public ApiResponse<?> createUrl(@PathVariable Long idx, @RequestBody UrlRequestDto url, HttpSession session) {
        ProjectResponseDto projectResponseDto = null;
        try {
            projectResponseDto = urlService.createUrl(idx, url, 1L);// 1 : dummy data
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

    @DeleteMapping("{projectIdx}/url/{idx}")
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
