package com.yapp.web1.exception.Common;

public class CommonException extends RuntimeException{

    public CommonException(String message){
        super(message);
    }

    public CommonException(String message, Throwable cause){
        super(message, cause);
    }
}
