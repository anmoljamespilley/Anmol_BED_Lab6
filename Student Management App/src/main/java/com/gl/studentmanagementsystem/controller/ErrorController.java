package com.gl.studentmanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    private final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @GetMapping({"/students/403", "/403"})
    public String accessDenied() {
        logger.info("Access denied page requested.");
        return "student/403";
    }
}
