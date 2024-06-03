package com.authenticationService.authentication.config;

import com.authenticationService.authentication.entity.UserCredential;
import com.authenticationService.authentication.repository.UserCredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCredRepository userCredRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> user = userCredRepository.findByName(username);
        return user.map(CustomUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("user not found with name :"+username));
    }
}
