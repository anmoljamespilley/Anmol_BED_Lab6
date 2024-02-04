package com.gl.studentmanagementsystem.service;

import com.gl.studentmanagementsystem.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> getUserByUsername(String username);

    void saveUser(User user);

    boolean isUsernameTaken(String username);
}
