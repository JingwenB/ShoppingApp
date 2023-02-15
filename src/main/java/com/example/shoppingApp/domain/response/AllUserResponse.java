package com.example.shoppingApp.domain.response;

import com.example.shoppingApp.domain.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllUserResponse {
    private String message;
    private List<User> users;
}
