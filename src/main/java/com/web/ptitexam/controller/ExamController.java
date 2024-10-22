package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.ExamService;
import com.web.ptitexam.service.UserService;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ExamController {

    private final ExamService examService;
    private final UserService userService;

    public ExamController(ExamService examService, UserService userService) {
        this.examService = examService;
        this.userService = userService;
    }

    @GetMapping(value = Constant.PAGE_TEACHER_EXAM)
    public String showTeacherExamPage(Model model) {
        UserDTO currentUser = userService.getCurrentUser();
        model.addAttribute("userDTO", currentUser);
        return Constant.PAGE_TEACHER_EXAM;
    }

    @GetMapping(value = Constant.PAGE_TEACHER_EXAM)
    public String showTeacherExamList(Model model, @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "search", defaultValue = "") String search) {

        try {
            /// TODO: Sẽ show sau
            return Constant.PAGE_TEACHER_CLASSROOM;

        } catch (Exception e) {
            return Constant.PAGE_LOGIN;
        }
    }

    @PostMapping(Constant.PAGE_TEACHER_EXAM_CREATE)
    public String createExam(@RequestParam("name") String name, Model model, RedirectAttributes redirectAttributes) {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return Constant.PAGE_TEACHER_EXAM_CREATE;
    }

}
