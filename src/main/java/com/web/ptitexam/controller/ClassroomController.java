package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClassroomController {

    private final UserService userService;

    public ClassroomController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = Constant.PAGE_TEACHER_CLASSROOM)
    public String showTeacherClassroom(Model model) {
        UserDTO currentUser = userService.getCurrentUser(); // Lấy người dùng hiện tại
        model.addAttribute("userDTO", currentUser); // Thêm UserDTO vào model
        return Constant.PAGE_TEACHER_CLASSROOM; // Trả về trang teacher/class
    }

}
