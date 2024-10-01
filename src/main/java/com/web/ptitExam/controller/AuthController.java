package com.web.ptitExam.controller;

import com.web.ptitExam.entity.User;
import com.web.ptitExam.repository.UserRepository;
import com.web.ptitExam.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
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
