package com.web.ptitexam.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.Result;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.ClassroomService;
import com.web.ptitexam.service.ExamService;
import com.web.ptitexam.service.ResultService;
import com.web.ptitexam.service.StudentService;
import com.web.ptitexam.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class ResultController {

    private final UserService userService;
    private final ClassroomService classroomService;
    private final StudentService studentService;
    private final ExamService examService;
    private final ResultService resultService;

    public ResultController(UserService userService, ClassroomService classroomService,
            StudentService studentService, ExamService examService, ResultService resultService) {
        this.userService = userService;
        this.classroomService = classroomService;
        this.studentService = studentService;
        this.examService = examService;
        this.resultService = resultService;
    }

    @GetMapping(value = Constant.TEACHER_SUBDIR + "/history")
    public String showTeacherResultHistory(Model model,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {

        UserDTO currentUser = userService.getCurrentUser();
        User user = userService.findByUsername(currentUser.getUsername());

        List<Result> results = resultService.findAll();

        model.addAttribute("results", results);
        model.addAttribute("totalItems", results.size());
        model.addAttribute("userDTO", currentUser);

        return Constant.PAGE_TEACHER_EXAM_HISTORY;
    }

    @GetMapping(value = Constant.STUDENT_SUBDIR + "/history")
    public String showStudentResultHistory(@RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, Model model) {
        UserDTO currentUser = userService.getCurrentUser();
        User user = userService.findByUsername(currentUser.getUsername());

        Pageable pageable = PageRequest.of(current - 1, pageSize);

        Page<Result> results = resultService.findByStudent(user.getStudent(), pageable);

        model.addAttribute("results", results.getContent());

        // trang hiện tại
        model.addAttribute("currentPage", current);

        // số lượng items mỗi trang
        model.addAttribute("pageSize", pageSize);

        // tổng số trang
        model.addAttribute("totalPages", results.getTotalPages());

        // tổng số items
        model.addAttribute("totalItems", results.getTotalElements());

        model.addAttribute("userDTO", currentUser);

        return Constant.PAGE_STUDENT_EXAM_HISTORY;
    }

    @PostMapping(value = Constant.TEACHER_SUBDIR + "/history/delete/{id}")
    public String postMethodName(@PathVariable("id") String resultId) {

        Result result = resultService.findByResultId(resultId);
        if (result == null) {
            return Constant.ERROR_PAGE;
        }
        resultService.removeResult(result);
        return "redirect:/teacher/history";
    }
    
}
