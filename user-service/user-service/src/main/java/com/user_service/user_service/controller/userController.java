package com.user_service.user_service.controller;

import com.user_service.user_service.entity.User;
import com.user_service.user_service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(userController.class);



    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        User user1 = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user1);
    }

    //creating rating fallback method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
        logger.info("Fallback executed cause service is down: ",ex.getMessage());
        User user = User.builder()
                .email("Dummy@email.com")
                .name("Dummy")
                .about("This is exception user")
                .userId("12345")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //all user get
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> userList = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
}
