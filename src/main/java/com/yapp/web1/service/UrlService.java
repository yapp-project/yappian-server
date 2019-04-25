package com.yapp.web1.service;

import com.yapp.web1.dto.req.UrlRequestDto;
import com.yapp.web1.dto.res.ProjectResponseDto;

/**
 * UrlService Interface
 */
public interface UrlService {
    /**
     * URL 생성
     *
     * @param url     생성할 Url 관련 데이터 - projectId 포함(UrlRequestDto)
     * @param userIdx 로그인 유저 정보
     * @return 생성한 url 데이터(UrlResponseDto), 해당 프로젝트의 url List
     * @throws checkUserPermission : join 되지 않은 유저가 url 생성시 예외
     * @see /v1/api/url
     */
     ProjectResponseDto createUrl(UrlRequestDto url, Long userIdx);

    /**
     * URL 삭제
     *
     * @param idx     삭제할 Url idx
     * @param userIdx 로그인 유저 정보
     * @throws checkUserPermission : join 되지 않은 유저가 url 삭제시 예외
     * @throws checkNotFound : 삭제하려는 url이 존재하지 않을시 예외
     * @see /v1/api/url/{idx}
     */
     void deleteUrl(final Long idx, Long userIdx );

}
