package com.example.shoppingApp.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class UserRequest {

    //    @NotNull(message = "id is required")
//    @Size(min = 13, max = 13, message = "ISBN must be exactly 13 characters")
    private Integer id;

    @NotNull(message = "email is required")
    @Email(message = "wrong email")
    @UniqueElements(message = "Email should be unique")
    private String email;

    @NotNull(message = "password is required")
    private String password;

    @NotNull(message = "username is required")
    @UniqueElements(message = "username should be unique")
    private String username;


}

