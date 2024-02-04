package com.gl.studentmanagementsystem.controller;

import com.gl.studentmanagementsystem.model.User;
import com.gl.studentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "student/login";
    }

    @PostMapping("/login")
    public String processLogin() {
        // Process login logic
        return "redirect:/student/list";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "student/signup";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "student/signup";
        }

        // Additional features like password hashing
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        if (userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("errorMessage", "Username already exists. Please choose a different username.");
            return "student/signup";
        }

        try {
            userService.saveUser(user);
            return "redirect:student/login";
        } catch (Exception e) {
            model.addAttribute("error", "Error processing signup: " + e.getLocalizedMessage());
            return "student/error";
        }
    }
}
