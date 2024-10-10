package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping(value = Constant.PAGE_LOGIN)
    public String getLoginForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return Constant.PAGE_LOGIN;
    }
}
