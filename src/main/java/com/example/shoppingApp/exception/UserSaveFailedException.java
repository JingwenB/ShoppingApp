package com.example.shoppingApp.exception;

public class UserSaveFailedException extends RuntimeException{


    public UserSaveFailedException(String message){
        super(String.format(message));

    }
}
