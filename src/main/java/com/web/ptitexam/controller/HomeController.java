package com.web.ptitexam.controller;

import com.web.ptitexam.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;
    private static final String TEACHER_HOME = "teacher/class";
    private static final String STUDENT_HOME = "student/exam";

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping (value = "/teacher/class")
    public String showStudentHomePage (Model model){
        return TEACHER_HOME;
    }

    @GetMapping (value = "/student/exam")
    public String showTeacherHomePage (Model model){
        return STUDENT_HOME;
    }
}
