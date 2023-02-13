package com.example.transactionmanagementdemo.domain.response;

import com.example.transactionmanagementdemo.domain.entity.User;
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
