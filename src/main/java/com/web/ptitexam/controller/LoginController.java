package com.web.ptitexam.controller;

import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginUser(@ModelAttribute("username") String username, @ModelAttribute("password") String password, Model model) {
        boolean isAuthenticated = userService.authenticate(username, password);
        if (isAuthenticated) {
            User user = userService.findByUsername(username);
            if (user.getRole().equals("ROLE_TEACHER")) {
                return "homepage/teacher";
            } else {
                return "homepage/student";
            }

        } else {
            return "login?error";
        }
    }



}
