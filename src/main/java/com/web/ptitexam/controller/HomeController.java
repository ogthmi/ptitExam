package com.web.ptitexam.controller;

import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    private final UserService userService;
    private static final String TEACHER_HOME = "teacher/class";
    private static final String STUDENT_HOME = "student/exam";

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/teacher/class")
    public String showTeacherHomePage(Model model) {
        UserDTO currentUser = userService.getCurrentUser(); // Lấy người dùng hiện tại
        model.addAttribute("userDTO", currentUser); // Thêm UserDTO vào model
        return "teacher/class"; // Trả về trang teacher/class
    }

    @GetMapping (value = "/student/exam")
    public String showStudentHomePage (Model model){
        return STUDENT_HOME;
    }
}
