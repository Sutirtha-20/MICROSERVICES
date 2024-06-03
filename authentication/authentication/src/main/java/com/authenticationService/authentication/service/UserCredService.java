package com.authenticationService.authentication.service;

import com.authenticationService.authentication.entity.UserCredential;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserCredService {

    public String saveUserCred(@RequestBody UserCredential userCredential);
}
