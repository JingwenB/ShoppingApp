package com.example.shoppingApp.domain.response;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
     private String message;
     private String exceptionType;
     private String status;
}