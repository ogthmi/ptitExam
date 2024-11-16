package com.web.ptitexam.controller;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.ClassroomDTO;
import com.web.ptitexam.dto.ExamDTO;
import com.web.ptitexam.dto.QuestionDTO;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.Question;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.Teacher;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.ClassroomService;
import com.web.ptitexam.service.ExamService;
import com.web.ptitexam.service.QuestionService;
import com.web.ptitexam.service.StudentService;
import com.web.ptitexam.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClassroomController {

    private final UserService userService;
    private final ClassroomService classroomService;
    private final StudentService studentService;
    private final ExamService examService;

    public ClassroomController(UserService userService, ClassroomService classroomService,
            StudentService studentService, ExamService examService) {
        this.userService = userService;
        this.classroomService = classroomService;
        this.studentService = studentService;
        this.examService = examService;
    }

    @GetMapping(value = Constant.PAGE_TEACHER_CLASSROOM)
    public String showTeacherClassroom(Model model,
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
            Page<Classroom> classroomPage = classroomService.findByTeacher(user.getTeacher(), search, pageable);

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
        } catch (Exception e) {
            return Constant.PAGE_LOGIN;
        }
    }

    @GetMapping(value = Constant.PAGE_STUDENT_CLASSROOM)
    public String showStudentClassroom(Model model, @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "search", defaultValue = "") String search) {
        UserDTO currentUser = userService.getCurrentUser();

        String key = "default";

        if ((!sort.equals("az") && !sort.equals("za") && !sort.equals("newest") && !sort.equals("oldest"))
                || sort.isEmpty()) {
            sort = "default";
        }

        model.addAttribute("sort", sort);

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

        Page<Classroom> classroomPage = classroomService.findByStudentsStudentId(currentUser.getStudentId(), search,
                pageable);

        if (search.equals("")) {
            search = "none";
        }

        model.addAttribute("classrooms", classroomPage.getContent());

        // trang hiện tại
        model.addAttribute("currentPage", current);

        // số lượng items mỗi trang
        model.addAttribute("pageSize", pageSize);

        // tổng số trang
        model.addAttribute("totalPages", classroomPage.getTotalPages());

        model.addAttribute("search", search);

        // tổng số items
        model.addAttribute("totalItems", classroomPage.getTotalElements());

        model.addAttribute("userDTO", currentUser);
        return Constant.PAGE_STUDENT_CLASSROOM;
    }

    @GetMapping(value = Constant.PAGE_TEACHER_CLASSROOM + "/update/{id}")
    public String showUpdateClassroom(Model model, @PathVariable("id") String id,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "examCurrent", defaultValue = "1") int examCurrent,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "key", defaultValue = "") String key) {
        try {
            UserDTO currentUser = userService.getCurrentUser();
            Classroom classroom = classroomService.findByClassId(id);

            if (classroom == null) {
                return "redirect:/teacher/classroom";
            }

            String[] keyList = { "studentId", "name", "major", "className" };

            boolean isValidKey = false;

            for (String k : keyList) {
                if (k.equals(key)) {
                    isValidKey = true;
                    break;
                }
            }

            if ((!sort.equals("az") && !sort.equals("za")) || sort.isEmpty()) {
                sort = "default";
                key = "default";
            }
            if (key == null || key.isEmpty() || !isValidKey) {
                key = "default";
                sort = "default";
            }

            Pageable StudentPageable;

            if (key != null && sort != null) {
                if ("az".equals(sort) && key != null && !key.isEmpty()) {
                    StudentPageable = PageRequest.of(current - 1, pageSize, Sort.by(Sort.Direction.ASC, key));
                } else {
                    StudentPageable = PageRequest.of(current - 1, pageSize);
                }

                if ("za".equals(sort) && key != null && !key.isEmpty()) {
                    StudentPageable = PageRequest.of(current - 1, pageSize, Sort.by(Sort.Direction.DESC, key));
                }
            } else {
                StudentPageable = PageRequest.of(current - 1, pageSize);
            }

            Page<Student> studentPage = studentService.findByClassrooms(classroom, search, StudentPageable);

            model.addAttribute("userDTO", currentUser);
            model.addAttribute("classroom", classroom);
            model.addAttribute("students", studentPage.getContent());
            // trang hiện tại
            model.addAttribute("currentPage", current);

            // số lượng items mỗi trang
            model.addAttribute("pageSize", pageSize);

            model.addAttribute("sort", sort);
            model.addAttribute("key", key);
            model.addAttribute("search", search);

            // tổng số trang
            model.addAttribute("totalPages", studentPage.getTotalPages());

            // tổng số items
            model.addAttribute("totalItems", studentPage.getTotalElements());

            // Exam Page:
            Pageable examPageable = PageRequest.of(examCurrent - 1, pageSize);
            Page<Exam> examPage = examService.findByClassAssigned(classroom, search, examPageable);

            /// TODO: sẽ làm sort sau (too lazy)

            model.addAttribute("exams", examPage.getContent());
            model.addAttribute("examCurrentPage", examCurrent);
            model.addAttribute("examPageSize", pageSize);
            model.addAttribute("examTotalPages", examPage.getTotalPages());
            model.addAttribute("examTotalItems", examPage.getTotalElements());

            return Constant.PAGE_TEACHER_UPDATE_CLASSROOM;
        } catch (Exception e) {
            System.out.println(e);
            return "redirect:/teacher/classroom";
        }

    }

    @PostMapping(Constant.PAGE_TEACHER_CLASSROOM + "/create")
    public String createClassroom(@RequestParam("name") String name, Model model,
            RedirectAttributes redirectAttributes) {

        System.out.println(SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User teacher = userService.findByUsername(user.getUsername());

        if (classroomService.existsByClassNameAndTeacher(name, teacher.getTeacher())) {
            redirectAttributes.addFlashAttribute("error", "Lớp học đã tồn tại.");
            return "redirect:/teacher/classroom";
        }

        ClassroomDTO classroomDto = new ClassroomDTO();
        classroomDto.setClassName(name);
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
    public String removeStudent(@PathVariable String classId, @PathVariable String studentId,
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

    @PostMapping(Constant.PAGE_STUDENT_CLASSROOM + "/delete/{id}")
    public String leaveClassroom(@PathVariable("id") String id, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        Classroom classroom = classroomService.findByClassId(id);

        if (classroom == null) {
            redirectAttributes.addFlashAttribute("error", "Lớp học không tồn tại.");
            return "redirect:" + request.getHeader("Referer");
        }

        classroomService.leaveClassroom(id);

        redirectAttributes.addFlashAttribute("success", "Rời khỏi lớp học thành công.");

        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping(Constant.PAGE_STUDENT_CLASSROOM + "/join")
    public String joinClassroom(@RequestParam("classId") String classId, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        String redirectUrl = "redirect:" + request.getHeader("Referer");

        Classroom classroom = classroomService.findByClassId(classId);

        UserDTO currentUser = userService.getCurrentUser();

        if (classroom == null) {
            redirectAttributes.addFlashAttribute("error", "Mã lớp học không tồn tại!");
            return redirectUrl;
        }

        if (classroom.getStudents().stream()
                .anyMatch(student -> student.getStudentId().equals(currentUser.getStudentId()))) {
            redirectAttributes.addFlashAttribute("error", "Bạn đã tham gia lớp học này!");
            return redirectUrl;
        }

        classroomService.addStudentToClassroom(classId, currentUser.getStudentId());

        redirectAttributes.addFlashAttribute("success", "Tham gia lớp học thành công.");

        return redirectUrl;
    }

    @GetMapping(Constant.PAGE_STUDENT_CLASSROOM + "/view/{id}")
    public String viewClassroomByStudent(@PathVariable String id, Model model,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "examCurrent", defaultValue = "1") int examCurrent,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "key", defaultValue = "") String key) {

        UserDTO currentUser = userService.getCurrentUser();
        Classroom classroom = classroomService.findByClassId(id);

        if (classroom == null) {
            return "redirect:/student/classroom";
        }

        String[] keyList = { "studentId", "name", "major", "className" };

        boolean isValidKey = false;

        for (String k : keyList) {
            if (k.equals(key)) {
                isValidKey = true;
                break;
            }
        }

        if ((!sort.equals("az") && !sort.equals("za")) || sort.isEmpty()) {
            sort = "default";
            key = "default";
        }
        if (key == null || key.isEmpty() || !isValidKey) {
            key = "default";
            sort = "default";
        }

        Pageable pageable;

        if (key != null && sort != null) {
            if ("az".equals(sort) && key != null && !key.isEmpty()) {
                pageable = PageRequest.of(current - 1, pageSize, Sort.by(Sort.Direction.ASC, key));
            } else {
                pageable = PageRequest.of(current - 1, pageSize);
            }

            if ("za".equals(sort) && key != null && !key.isEmpty()) {
                pageable = PageRequest.of(current - 1, pageSize, Sort.by(Sort.Direction.DESC, key));
            }
        } else {
            pageable = PageRequest.of(current - 1, pageSize);
        }

        Page<Student> studentPage = studentService.findByClassrooms(classroom, search, pageable);

        model.addAttribute("userDTO", currentUser);
        model.addAttribute("classroom", classroom);
        model.addAttribute("students", studentPage.getContent());
        // trang hiện tại
        model.addAttribute("currentPage", current);

        // số lượng items mỗi trang
        model.addAttribute("pageSize", pageSize);

        model.addAttribute("sort", sort);
        model.addAttribute("key", key);
        model.addAttribute("search", search);

        // tổng số trang
        model.addAttribute("totalPages", studentPage.getTotalPages());

        // tổng số items
        model.addAttribute("totalItems", studentPage.getTotalElements());

        // Exam Page:
        Pageable examPageable = PageRequest.of(examCurrent - 1, pageSize);
        Page<Exam> examPage = examService.findByClassAssigned(classroom, search, examPageable);

        /// TODO: sẽ làm sort sau (too lazy)

        model.addAttribute("exams", examPage.getContent());
        model.addAttribute("examCurrentPage", examCurrent);
        model.addAttribute("examPageSize", pageSize);
        model.addAttribute("examTotalPages", examPage.getTotalPages());
        model.addAttribute("examTotalItems", examPage.getTotalElements());

        return Constant.PAGE_STUDENT_VIEW_CLASSROOM;
    }

}