package com.example.shoppingApp.exception;

public class RequestPageOverTotalPageException extends RuntimeException{

    public RequestPageOverTotalPageException(String message){
        super(String.format(message));

    }
}
