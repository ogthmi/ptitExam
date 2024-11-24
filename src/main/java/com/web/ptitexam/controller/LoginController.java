package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.ForgotPassword;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.EmailService;
import com.web.ptitexam.service.ForgotPasswordService;
import com.web.ptitexam.service.UserService;

import java.time.Instant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserService userService;
    private final EmailService emailService;
    private final ForgotPasswordService forgotPasswordService;

    public LoginController(UserService userService, EmailService emailService,
            ForgotPasswordService forgotPasswordService) {
        this.userService = userService;
        this.emailService = emailService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @GetMapping(value = Constant.PAGE_LOGIN)
    public String getLoginForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return Constant.PAGE_LOGIN;
    }

    @GetMapping("/access-deny")
    public String getForbiddenPage(Model model) {
        return "403/deny";
    }

    @GetMapping("/forgot-password")
    public String getForgotPasswordPage(Model model) {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Email không tồn tại!");
            return "redirect:/forgot-password";
        }

        ForgotPassword forgotPassword = forgotPasswordService.findByEmail(email);

        String token = userService.generateToken();

        if (forgotPassword == null) {
            forgotPasswordService.createToken(email, token);
        } else {
            if (forgotPassword.getExpiredAt().compareTo(Instant.ofEpochMilli(System.currentTimeMillis())) > 0) {
                redirectAttributes.addFlashAttribute("error",
                        "Yêu cầu lấy lại mật khẩu đã được gửi, vui lòng đợi 3 phút trước khi gửi yêu cầu mới!");
                return "redirect:/forgot-password";
            } else {
                forgotPassword.setToken(token);
                forgotPassword.setExpiredAt(forgotPassword.getExpiredAt().plusSeconds(60 * 3)); // 3 phút
                forgotPasswordService.save(forgotPassword);
            }
        }

        String urlVerify = "http://localhost:8080/ptit-exam/reset-password?token=" + token;

        emailService.sendEmailFromTemplateSync(email, "Lấy lại mật khẩu",
                "reset.password", urlVerify);

        redirectAttributes.addFlashAttribute("success",
                "Vui lòng kiểm tra email để lấy lại mật khẩu, việc này có thể mất vài phút!");

        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String getResetPasswordPage(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        ForgotPassword forgotPassword = forgotPasswordService.findByToken(token);

        if (forgotPassword == null) {
            return "redirect:/login";
        }

        if (forgotPassword.getExpiredAt().compareTo(Instant.ofEpochMilli(System.currentTimeMillis())) < 0) {
            redirectAttributes.addFlashAttribute("error", "Đã hết hạn yêu cầu lấy lại mật khẩu!");
            return "redirect:/forgot-password";
        }

        String newPassword = userService.generateToken();

        User user = userService.getUserByEmail(forgotPassword.getEmail());

        userService.updateUserPassword(user, newPassword);

        forgotPasswordService.delete(forgotPassword);

        emailService.sendEmailFromTemplateSync(forgotPassword.getEmail(), "Mật khẩu mới",
                "new.password", newPassword);

        redirectAttributes.addFlashAttribute("success",
                "Đặt lại mật khẩu thành công, Mật khẩu mới đã được gửi về email của bạn!");

        return "redirect:/forgot-password";
    }

}
