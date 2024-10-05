package com.web.ptitexam.controller;

import com.web.ptitexam.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;
    private static final String TEACHER_HOME = "teacher";
    private static final String STUDENT_HOME = "student";

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping (value = "/student")
    public String showStudentHomePage (Model model){
        return "/student";
    }

    @GetMapping (value = "/teacher")
    public String showTeacherHomePage (Model model){
        return "/teacher";
    }
}
