package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class LogController {

    private final UserService userService;

    public LogController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = Constant.PAGE_LOGIN)
    public String getLoginForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return Constant.PAGE_LOGIN;
    }

    private String getRedirectUrlBasedOnRole (String role) {
        return switch (role) {
            case Constant.ROLE_ADMIN -> Constant.PAGE_ADMIN_DASHBOARD;
            case Constant.ROLE_STUDENT -> Constant.PAGE_STUDENT_CLASSROOM;
            case Constant.ROLE_TEACHER -> Constant.PAGE_TEACHER_CLASSROOM;
            default -> Constant.PAGE_LOGIN;
        };
    }

    @GetMapping(value = Constant.PAGE_REGISTER)
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return Constant.PAGE_REGISTER;
    }

    @PostMapping(value = Constant.PAGE_REGISTER)
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO,
                               BindingResult bindingResult,
                               Model model) {
        if (userService.isUsernameTaken(userDTO.getUsername())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại.");
            return Constant.PAGE_REGISTER;
        }

        String password = userDTO.getPassword();
        if (password == null || password.length() < 8) {
            model.addAttribute("error", "Mật khẩu phải có tối thiểu 8 kí tự.");
            return Constant.PAGE_REGISTER;
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại.");
            return Constant.PAGE_REGISTER;
        }

        userService.registerUser(userDTO); // Đăng ký người dùng
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userDTO.getRole()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDTO.getUsername(),   // Username
                null,                    // Password (unnecessary)
                authorities              // Authorities list
        );
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String redirectUrl = getRedirectUrlBasedOnRole(userDTO.getRole());
        model.addAttribute("success", "Đăng ký thành công.");

        return Constant.REDIRECT + redirectUrl;
    }
}
