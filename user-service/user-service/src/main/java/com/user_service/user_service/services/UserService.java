package com.user_service.user_service.services;

import com.user_service.user_service.entity.User;

import java.util.List;

public interface UserService {
    //define user opearation

    //create
    public User saveUser(User user);

    //get all user
    public List<User> getAllUser();

    //get user by id
    public User getUserById(String userId);
}
