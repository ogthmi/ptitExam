package com.web.ptitexam.controller;

import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.UserService;
import org.springframework.boot.context.properties.bind.BindResult;
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
public class AuthController {

    private final UserService userService;
    private static final String LOGIN_PAGE = "login";
    private static final String TEACHER_HOME = "teacher/class";
    private static final String STUDENT_HOME = "student/exam";
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
            model.addAttribute("error","Dữ liệu nhập vào không hợp lệ");
            return LOGIN_PAGE;
        }

        // Kết hợp kiểm tra sự tồn tại của tên đăng nhập và xác thực mật khẩu
        UserDTO authenticatedUser = userService.authenticateAndGetUser(userDTO.getUsername(), userDTO.getPassword());
        if (authenticatedUser == null) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác.");
            return LOGIN_PAGE;
        }

        String redirectUrl = getRedirectUrlBasedOnRole(authenticatedUser.getRole());
        return "redirect:/" + redirectUrl;
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
        if (userService.isUsernameTaken(userDTO.getUsername())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại.");
            return REGISTER_PAGE; // Quay lại trang đăng ký với thông báo lỗi
        }

        // Kiểm tra mật khẩu có null hay không
        String password = userDTO.getPassword();
        if (password == null || password.length() < 8) {
            model.addAttribute("error", "Mật khẩu phải có tối thiểu 8 kí tự.");
            return REGISTER_PAGE; // Quay lại trang đăng ký với thông báo lỗi
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại.");
            return REGISTER_PAGE;
        }

        // Đăng ký người dùng mới và lưu thông tin người dùng
        userService.registerUser(userDTO); // Đăng ký người dùng

        // Tạo danh sách quyền (Authorities) dựa trên vai trò người dùng
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userDTO.getRole()));

        // Tạo Authentication với danh sách quyền của người dùng
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDTO.getUsername(),   // Username
                null,                    // Password không cần thiết
                authorities               // Danh sách quyền (authorities)
        );

        // Đặt thông tin đăng nhập vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Lấy URL chuyển hướng dựa trên vai trò của người dùng
        String redirectUrl = getRedirectUrlBasedOnRole(userDTO.getRole());
        model.addAttribute("message", "Đăng ký thành công!");

        // Chuyển hướng về trang mong muốn sau khi đăng ký thành công
        return "redirect:" + redirectUrl;
    }



}
