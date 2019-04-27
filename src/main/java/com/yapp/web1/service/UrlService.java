package com.yapp.web1.service;

import com.yapp.web1.dto.req.UrlRequestDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UrlResponseDto;

import java.util.List;

/**
 * UrlService Interface
 */
public interface UrlService {

    /**
     * 해당 프로젝트의 URL 불러오기
     * @param idx projectIdx
     * @param session 로그인 유저 정보
     * @return 해당 프로젝트의 url List
     * @throws Exception join한 유저만 url 생성 가능
     * @see /v1/api/{idx}/url
     */
    List<UrlResponseDto> getUrl(Long projectId);

    /**
     * URL 생성
     * @param idx projectIdx
     * @param url     생성할 Url 관련 데이터 - projectId 포함(UrlRequestDto)
     * @param userIdx 로그인 유저 정보
     * @return 생성한 url 데이터(UrlResponseDto), 해당 프로젝트의 url List
     * @throws checkUserPermission : join 되지 않은 유저가 url 생성시 예외
     * @see /v1/api/url
     */
     ProjectResponseDto createUrl(Long projectIdx, UrlRequestDto url, Long userIdx);

    /**
     * URL 삭제
     * @param projectIdx     project idx
     * @param idx     삭제할 Url idx
     * @param userIdx 로그인 유저 정보
     * @throws checkUserPermission : join 되지 않은 유저가 url 삭제시 예외
     * @throws checkNotFound : 삭제하려는 url이 존재하지 않을시 예외
     * @see /v1/api/url/{idx}
     */
     void deleteUrl(Long projectIdx, final Long idx, Long userIdx );

}
