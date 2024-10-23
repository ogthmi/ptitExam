package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.ClassroomDTO;
import com.web.ptitexam.dto.ExamDTO;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.ExamService;
import com.web.ptitexam.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String showTeacherExamPage(Model model,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "search", defaultValue = "") String search) {
        try {
            UserDTO currentUser = userService.getCurrentUser();
            User user = userService.findByUsername(currentUser.getUsername());

            String key = "default";

            if ((!sort.equals("az") && !sort.equals("za") && !sort.equals("newest") && !sort.equals("oldest"))
                    || sort.isEmpty()) {
                sort = "default";
            }

            model.addAttribute("sort", sort);
            model.addAttribute("search", search);

            Pageable pageable;
            if ("az".equals(sort)) {
                key = "className";
                pageable = PageRequest.of(current - 1, pageSize, Sort.by(Sort.Direction.ASC, key));
            } else if ("za".equals(sort) && key != null && !key.isEmpty()) {
                key = "className";
                pageable = PageRequest.of(current - 1, pageSize, Sort.by(Sort.Direction.DESC, key));
            } else if ("newest".equals(sort)) {
                key = "classCreatedAt";
                pageable = PageRequest.of(current - 1, pageSize, Sort.by(Sort.Direction.DESC, key));
            } else if ("oldest".equals(sort)) {
                key = "classCreatedAt";
                pageable = PageRequest.of(current - 1, pageSize, Sort.by(Sort.Direction.ASC, key));
            } else {
                pageable = PageRequest.of(current - 1, pageSize);
            }

            model.addAttribute("userDTO", currentUser);

            Page<Exam> examPage = examService.findByTeacher(user.getTeacher(), search, pageable);

            model.addAttribute("exam", examPage.getContent());

            // trang hiện tại
            model.addAttribute("currentPage", current);

            // số lượng items mỗi trang
            model.addAttribute("pageSize", pageSize);

            // tổng số trang
            model.addAttribute("totalPages", examPage.getTotalPages());

            // tổng số items
            model.addAttribute("totalItems", examPage.getTotalElements());
            return Constant.PAGE_TEACHER_EXAM;
        } catch (Exception e) {
            return Constant.PAGE_LOGIN;
        }
    }

    @GetMapping(Constant.PAGE_TEACHER_EXAM_CREATE)
    public String showTeacherExamCreatePage(Model model) {

        UserDTO currentUser = userService.getCurrentUser();
        model.addAttribute("userDTO", currentUser);

        return Constant.PAGE_TEACHER_EXAM_CREATE;
    }

    @PostMapping(Constant.PAGE_TEACHER_EXAM + "/create")
    public String createExam(@RequestParam("examTitle") String title, @RequestParam("examDuration") int examDuration,
            Model model,
            RedirectAttributes redirectAttributes) {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        ExamDTO examDTO = new ExamDTO();
        examDTO.setExamTitle(title);
        examDTO.setExamDuration(examDuration);
        examService.createExam(examDTO);
        redirectAttributes.addFlashAttribute("success", "Tạo đề thi thành công.");

        return "redirect:/teacher/exam";
    }

    @PostMapping(Constant.PAGE_TEACHER_CLASSROOM + "/udpate/{id}")
    public String updateClassroom(@PathVariable("id") String id, @RequestParam("name") String title, Model model,
            RedirectAttributes redirectAttributes) {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        ExamDTO examDTO = new ExamDTO();
        examDTO.setExamTitle(title);
        examService.createExam(examDTO);
        redirectAttributes.addFlashAttribute("success", "Cập nhật đề thi thành công.");

        return "redirect:/teacher/exam";
    }
}
