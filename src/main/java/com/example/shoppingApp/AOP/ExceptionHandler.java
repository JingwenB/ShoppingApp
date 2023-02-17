package com.example.shoppingApp.AOP;

import com.example.shoppingApp.domain.response.ErrorResponse;
import com.example.shoppingApp.exception.*;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.HibernateObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class ExceptionHandler {

//    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<ErrorResponse> handleException(Exception e){
//        return new ResponseEntity<>(
//                ErrorResponse.builder()
//                        .message("Using ExceptionHandler for handling all Exception")
//                        .build(),
//                HttpStatus.INTERNAL_SERVER_ERROR // status code can be customized
//        );
//    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            NotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e){
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .message(e.getMessage())
                        .exceptionType(e.toString().split(":")[0])
                        .status(String.valueOf( HttpStatus.NOT_FOUND ))
                        .build(),
                HttpStatus.NOT_FOUND // 404
        );
    }



    // user login
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            InvalidCredentialsException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(Exception e){
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .message(e.getMessage())
                        .exceptionType(e.toString().split(":")[0])
                        .status(String.valueOf(HttpStatus.FORBIDDEN))
                        .build(),
                HttpStatus.FORBIDDEN
        );
    }

    // user create order with not enough in stock
    // generic dao add failed, delete failed
    // admin update with negative stock
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            SaveFailedException.class,
            DeleteFailedException.class,
    })
    public ResponseEntity<ErrorResponse> handleMultipleInternalExceptions(Exception e){
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .message(e.getMessage())
                        .exceptionType(e.toString().split(":")[0])
                        .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            NotEnoughInventoryException.class,
            InvalidProductUpdateInfoException.class,
            RequestPageOverTotalPageException.class,
            OrderCancelFailedException.class,
            OrderCompleteFailedException.class,
            DuplicatedProductInWatcchList.class
    })
    public ResponseEntity<ErrorResponse> handleMultipleBadRequestExceptions(Exception e){
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .message(e.getMessage())
                        .exceptionType(e.toString().split(":")[0])
                        .status(String.valueOf(HttpStatus.BAD_REQUEST))
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }


}
