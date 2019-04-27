package com.yapp.web1.exception.Url;

public class UrlException extends RuntimeException{

    public UrlException(String message){
        super(message);
    }

    public UrlException(String message, Throwable cause){
        super(message, cause);
    }
}
