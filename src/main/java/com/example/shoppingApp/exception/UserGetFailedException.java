package com.example.shoppingApp.exception;

public class UserGetFailedException extends RuntimeException{


    public UserGetFailedException(String message){
        super(String.format(message));

    }
}
