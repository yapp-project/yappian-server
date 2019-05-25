package com.yapp.web1.controller;

import com.yapp.web1.common.AuthUtils;
import com.yapp.web1.dto.req.UrlRequestDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UrlResponseDto;
import com.yapp.web1.exception.NoPermissionException;
import com.yapp.web1.exception.NotFoundException;
import com.yapp.web1.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Url Controller
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @version 1.1
 * @since 0.0.4
 */
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api")
@RestController
@Api(tags = "산출물(URL) APIs")
public class UrlController {

    private final UrlService urlService;

    /**
     * 해당 프로젝트의 URL 불러오기
     * @param projectIdx
     * @return 해당 프로젝트의 url List
     * @throws Exception join한 유저만 url 생성 가능
     * @see /api/project/{projectIdx}/url/list
     */
    @GetMapping("/project/{projectIdx}/url/list")
    @ApiOperation(value = "각 프로젝트의 URL 목록 호출 (인증 필요 없음)")
    public ResponseEntity<?> getUrl(@PathVariable  @ApiParam(value = "조회할 Project idx", example = "1") Long projectIdx) {
        try {
            List<UrlResponseDto> urlResponseDto = urlService.getUrl(projectIdx);
            return new ResponseEntity<>(urlResponseDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * URL 생성
     * @param projectIdx
     * @param url     생성할 Url 관련 데이터
     * @return  해당 프로젝트정보와 url List
     * @throws Exception join한 유저만 url 생성 가능
     * @see /api/project/{projectIdx}/url
     */
    @PostMapping("project/{projectIdx}/url")
    @ApiOperation(value = "새로운 URL 생성 (참여 유저 가능)")
    public ResponseEntity<?> createUrl(@PathVariable @ApiParam(value = "URL 생성할 Project idx", example = "1") Long projectIdx, @RequestBody UrlRequestDto url) {
        try {
            ProjectResponseDto projectResponseDto = urlService.createUrl(projectIdx, url, AuthUtils.getCurrentAccount().getIdx());
            return new ResponseEntity<>(projectResponseDto, HttpStatus.CREATED);
        } catch (NoPermissionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Url 삭제
     * @param idx - urlIdx
     * @see /api/project/{projectIdx}/url/{idx}
     */
    @DeleteMapping("project/{projectIdx}/url/{idx}")
    @ApiOperation(value = "URL 삭제 (작성자 가능)")
    public ResponseEntity<?> deleteUrl(@PathVariable @ApiParam(value = "URL 삭제할 Project idx", example = "1") final Long projectIdx,
                                       @PathVariable @ApiParam(value = "삭제할 Url idx", example = "1") final Long idx) {
        try {
            urlService.deleteUrl(projectIdx, idx, AuthUtils.getCurrentAccount().getIdx());
            return new ResponseEntity<>("Url 삭제 성공", HttpStatus.NO_CONTENT);
        }catch (NoPermissionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
