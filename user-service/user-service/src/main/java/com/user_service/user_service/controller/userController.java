package com.user_service.user_service.controller;

import com.user_service.user_service.entity.User;
import com.user_service.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class userController {
    @Autowired
    private UserService userService;

    //create
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        User user1 = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user1);
    }

    //all user get
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> userList = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
}
