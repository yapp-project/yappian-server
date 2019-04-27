package com.yapp.web1.exception.Url;

/**
 * 리소스 없음 에러
 */
public class NotFoundException extends UrlException {

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
