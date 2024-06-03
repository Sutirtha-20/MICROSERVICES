package com.authenticationService.authentication.repository;

import com.authenticationService.authentication.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredRepository extends JpaRepository<UserCredential,Integer> {
    public Optional<UserCredential> findByName(String username);
}
