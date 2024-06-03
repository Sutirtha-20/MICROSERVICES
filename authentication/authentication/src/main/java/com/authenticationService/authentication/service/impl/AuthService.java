package com.authenticationService.authentication.service.impl;

import com.authenticationService.authentication.entity.UserCredential;
import com.authenticationService.authentication.repository.UserCredRepository;
import com.authenticationService.authentication.service.UserCredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


//to implement jwt we need 3 methods one to save user details in DB
//one to get the JWT
//one to validate the received JWT

@Service
public class AuthService implements UserCredService {
    @Autowired
    private UserCredRepository userCredRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private JwtService jwtService;

    @Override
    public String saveUserCred(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredRepository.save(userCredential);
        logger.info("user saved");
        return "user added to system";
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
