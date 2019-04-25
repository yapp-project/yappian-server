package com.yapp.web1.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * REST API 사용시 범용적으로 사용되는 응답 정의
 *
 * @param <T> 응답시 데이터 타입
 */
@Getter
public class ApiResponse<T> {


    private int code;

    /**
     * REST API Status 코드
     *
     * @return Status 코드
     */
    private String status;

    /**
     * 프론트엔드 개발의 편의를 위한 서버측의 메세지
     *
     * @return 서버 측의 메세지
     */
    private String message;

    /**
     * API 응답 데이터
     *
     * @return 데이터
     */
    private T data;

    @Builder
    public ApiResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        this.data = data;
    }
}
