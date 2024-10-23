package com.web.ptitexam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.ptitexam.dto.StudentDTO;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.StudentService;
import com.web.ptitexam.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/student/search")
    public List<StudentDTO> searchStudents(@RequestParam("id") String id) {
        return userService.searchStudents(id);
    }
}
