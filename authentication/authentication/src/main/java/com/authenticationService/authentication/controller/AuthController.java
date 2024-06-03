package com.authenticationService.authentication.controller;

import com.authenticationService.authentication.dto.AuthRequest;
import com.authenticationService.authentication.entity.UserCredential;
import com.authenticationService.authentication.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential userCredential){
        return authService.saveUserCred(userCredential);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername());
        }else {
            throw new RuntimeException("invalid user");
        }
    }

    @GetMapping("/validate")
    public void validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
    }
}
