package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.service.UserService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.plaf.PanelUI;

@Controller
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping (value = Constant.PAGE_USER_PROFILE)
    public String showUserProfilePage (Model model){
        UserDTO currentUser = userService.getCurrentUser();
        System.out.println(currentUser);
        model.addAttribute("userDTO", currentUser);
        return Constant.PAGE_USER_PROFILE;
    }

}
