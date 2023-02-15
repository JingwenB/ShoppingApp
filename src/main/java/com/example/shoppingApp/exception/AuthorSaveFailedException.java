package com.example.shoppingApp.exception;

public class AuthorSaveFailedException extends RuntimeException{


    public AuthorSaveFailedException(String message){
        super(String.format(message));

    }
}
