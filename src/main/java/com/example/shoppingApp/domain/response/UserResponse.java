package com.example.shoppingApp.domain.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
}