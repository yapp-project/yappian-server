package com.yapp.web1.exception;

/**
 * 리소스 없음 에러
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
