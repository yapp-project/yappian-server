package com.yapp.web1.exception.Common;

/**
 * url 접근 불허가 에러
 */
public class NoPermissionException extends RuntimeException {

    public NoPermissionException(String message){
        super(message);
    }

    public NoPermissionException(String message, Throwable cause){
        super(message, cause);
    }
}
