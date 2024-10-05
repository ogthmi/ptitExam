package com.web.ptitexam.controller;

import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.UserService;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;
    private static final String LOGIN_PAGE = "login";
    private static final String TEACHER_HOME = "teacher";
    private static final String STUDENT_HOME = "student";
    private static final String REGISTER_PAGE = "register";

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String getLoginForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return LOGIN_PAGE;
    }

    @PostMapping(value = "/login")
    public String loginUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Dữ liệu nhập vào không hợp lệ");
            return LOGIN_PAGE;
        }
        if (userService.authenticate(userDTO.getUsername(), userDTO.getPassword())) {
            User user = userService.findByUsername(userDTO.getUsername());
            String redirectUrl = getRedirectUrlBasedOnRole(user.getRole());
            return "redirect:/" + redirectUrl;
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác");
            return LOGIN_PAGE;
        }
    }


    private String getRedirectUrlBasedOnRole (String role) {
        return switch (role) {
            case "ROLE_TEACHER" -> TEACHER_HOME;
            case "ROLE_STUDENT" -> STUDENT_HOME;
            default -> LOGIN_PAGE;
        };
    }

    @GetMapping(value = "/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return REGISTER_PAGE;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return REGISTER_PAGE + "?error";
        }
        userService.registerUser(userDTO);
        String redirectUrl = getRedirectUrlBasedOnRole(userDTO.getRole());
        return "redirect:/" + redirectUrl;
    }

}
