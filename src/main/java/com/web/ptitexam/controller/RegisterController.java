package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    private String getRedirectUrlBasedOnRole(String role) {
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
            Model model,
            RedirectAttributes redirectAttributes) {
        if (userService.isUsernameTaken(userDTO.getUsername())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại.");
            return Constant.PAGE_REGISTER;
        }

        if (userService.isEmailTaken(userDTO.getEmail())) {
            model.addAttribute("error", "Email đã tồn tại.");
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

        userService.registerUser(userDTO);
        userService.authenticateRegistration(userDTO);

        String redirectUrl = getRedirectUrlBasedOnRole(userDTO.getRole());
        redirectAttributes.addFlashAttribute("success", "Đăng ký người dùng thành công.");
        return Constant.REDIRECT + redirectUrl;
    }
}
