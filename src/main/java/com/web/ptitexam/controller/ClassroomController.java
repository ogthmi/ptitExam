package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.ClassroomDTO;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.Teacher;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.ClassroomService;
import com.web.ptitexam.service.StudentService;
import com.web.ptitexam.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ClassroomController {

    private final UserService userService;
    private final ClassroomService classroomService;
    private final StudentService studentService;

    public ClassroomController(UserService userService, ClassroomService classroomService,
            StudentService studentService) {
        this.userService = userService;
        this.classroomService = classroomService;
        this.studentService = studentService;
    }

    @GetMapping(value = Constant.PAGE_TEACHER_CLASSROOM)
    public String showTeacherClassroom(Model model,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        UserDTO currentUser = userService.getCurrentUser();
        User user = userService.findByUsername(currentUser.getUsername());

        Pageable pageable = PageRequest.of(current - 1, pageSize);
        Page<Classroom> classroomPage = classroomService.findByTeacher(user.getTeacher(), pageable);

        model.addAttribute("userDTO", currentUser);
        model.addAttribute("classrooms", classroomPage.getContent());

        // trang hiện tại
        model.addAttribute("currentPage", current);

        // số lượng items mỗi trang
        model.addAttribute("pageSize", pageSize);

        // tổng số trang
        model.addAttribute("totalPages", classroomPage.getTotalPages());

        // tổng số items
        model.addAttribute("totalItems", classroomPage.getTotalElements());

        return Constant.PAGE_TEACHER_CLASSROOM;
    }

    @GetMapping(value = Constant.PAGE_STUDENT_CLASSROOM)
    public String showStudentClassroom(Model model) {
        UserDTO currentUser = userService.getCurrentUser();
        model.addAttribute("userDTO", currentUser);
        return Constant.PAGE_STUDENT_CLASSROOM;
    }

    @GetMapping(value = Constant.PAGE_TEACHER_CLASSROOM + "/update/{id}")
    public String showUpdateClassroom(Model model, @PathVariable("id") String id,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        try {
            UserDTO currentUser = userService.getCurrentUser();
            Classroom classroom = classroomService.findByClassId(id);

            if (classroom == null) {
                return "redirect:/teacher/classroom";
            }

            Pageable pageable = PageRequest.of(current - 1, pageSize);
            Page<Student> studentPage = studentService.findByClassrooms(classroom, pageable);

            model.addAttribute("userDTO", currentUser);
            model.addAttribute("classroom", classroom);
            model.addAttribute("students", studentPage.getContent());
            // trang hiện tại
            model.addAttribute("currentPage", current);

            // số lượng items mỗi trang
            model.addAttribute("pageSize", pageSize);

            // tổng số trang
            model.addAttribute("totalPages", studentPage.getTotalPages());

            // tổng số items
            model.addAttribute("totalItems", studentPage.getTotalElements());

            return Constant.PAGE_TEACHER_UPDATE_CLASSROOM;
        } catch (Exception e) {
            return "redirect:/login";
        }

    }

    @PostMapping(Constant.PAGE_TEACHER_CLASSROOM + "/create")
    public String createClassroom(@RequestParam("name") String name, Model model,
            RedirectAttributes redirectAttributes) {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User teacher = userService.findByUsername(user.getUsername());

        if (classroomService.existsByClassNameAndTeacher(name, teacher.getTeacher())) {
            redirectAttributes.addFlashAttribute("error", "Lớp học đã tồn tại.");
            return "redirect:/teacher/classroom";
        }

        ClassroomDTO classroomDto = new ClassroomDTO();
        classroomDto.setClassName(name);
        System.out.println("Classroom name: " + classroomDto.getClassName());
        classroomService.createClassroom(classroomDto);
        redirectAttributes.addFlashAttribute("success", "Tạo mới lớp học thành công.");
        return "redirect:/teacher/classroom";
    }

    @PostMapping(Constant.PAGE_TEACHER_CLASSROOM + "/update/{id}")
    public String updateClassroom(@PathVariable("id") String id, @RequestParam("name") String name, Model model,
            RedirectAttributes redirectAttributes) {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User teacher = userService.findByUsername(user.getUsername());

        Classroom classroom = classroomService.findByClassId(id);

        if (classroomService.existsByClassNameAndTeacher(name, teacher.getTeacher())
                && !classroom.getClassName().equals(name)) {
            redirectAttributes.addFlashAttribute("error", "Lớp học đã tồn tại.");
            return "redirect:/teacher/classroom";
        }

        ClassroomDTO classroomDto = new ClassroomDTO();
        classroomDto.setClassName(name);
        System.out.println("Classroom name: " + classroomDto.getClassName());
        classroomService.updateClassroomById(id, classroomDto);

        redirectAttributes.addFlashAttribute("success", "Cập nhật lớp học thành công.");
        return "redirect:/teacher/classroom";
    }

    @PostMapping(Constant.PAGE_TEACHER_CLASSROOM + "/delete/{id}")
    public String deleteClassroom(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        Classroom classroom = classroomService.findByClassId(id);
        if (classroom == null) {
            redirectAttributes.addFlashAttribute("error", "Lớp học không tồn tại.");
            return "redirect:/teacher/classroom";
        }
        classroomService.deleteClassroomById(id);
        redirectAttributes.addFlashAttribute("success", "Xóa lớp học thành công.");
        return "redirect:/teacher/classroom";
    }

    @PostMapping(Constant.PAGE_TEACHER_CLASSROOM + "/add-student")
    public String addStudentToClassroom(@RequestParam String studentId, @RequestParam String classId,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Student student = studentService.findByStudentId(studentId);
        System.out.println(studentId);
        String redirectUrl = "redirect:" + request.getHeader("Referer");
        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "Sinh viên không tồn tại!");
            return redirectUrl;
        }

        Classroom classroom = classroomService.findByClassId(classId);

        if (classroom.getStudents().contains(student)) {
            redirectAttributes.addFlashAttribute("error", "Sinh viên đã tồn tại trong lớp học.");
            return redirectUrl;
        }

        classroomService.addStudentToClassroom(classId, studentId);

        redirectAttributes.addFlashAttribute("success", "Thêm sinh viên vào lớp học thành công.");
        return redirectUrl;
    }

    @GetMapping(Constant.PAGE_TEACHER_CLASSROOM + "/remove-student/{classId}/{studentId}")
    public String postMethodName(@PathVariable String classId, @PathVariable String studentId,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // TODO: process POST request
        Classroom classroom = classroomService.findByClassId(classId);
        String redirectUrl = "redirect:" + request.getHeader("Referer");
        if (classroom == null) {
            redirectAttributes.addFlashAttribute("error", "Lớp học không tồn tại.");
            return redirectUrl;
        }

        Student student = studentService.findByStudentId(studentId);

        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "Sinh viên không tồn tại.");
            return redirectUrl;
        }

        if (!classroom.getStudents().contains(student)) {
            redirectAttributes.addFlashAttribute("error", "Sinh viên không tồn tại trong lớp học.");
            return redirectUrl;
        }

        classroomService.removeStudentFromClassroom(classId, studentId);

        redirectAttributes.addFlashAttribute("success", "Xóa sinh viên khỏi lớp học thành công.");
        return redirectUrl;

    }

}
