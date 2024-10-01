package com.web.ptitExam.controller;

import com.web.ptitExam.dto.UserDTO;
import com.web.ptitExam.entity.User;
import com.web.ptitExam.service.UserService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "homepage";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
