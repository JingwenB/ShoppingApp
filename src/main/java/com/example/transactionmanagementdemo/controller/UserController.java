package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.response.UserResponse;

import com.example.transactionmanagementdemo.exception.AuthorSaveFailedException;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import com.example.transactionmanagementdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUserSuccess(){
        return userService.getAll();
    }

    @GetMapping("/id/{id}")
    public UserResponse getUserById(@PathVariable int id)  throws UserGetFailedException {

        User user = userService.getById(id);
        return UserResponse.builder()
                .message("Returning user with id: " + id)
                .user(user)
                .build();
    }

    @PutMapping ("/register")
    public UserResponse saveUser(@RequestBody User user)  throws UserSaveFailedException {

        userService.saveUser(user);

        return UserResponse.builder()
                .message("User saved, committing...")
                .user(user)
                .build();
    }

    @DeleteMapping ("/id/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id)  throws UserGetFailedException {
        userService.deleteUser(id);

        return new ResponseEntity<>("User deleted.", HttpStatus.OK);
    }

}
