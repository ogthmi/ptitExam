package com.web.ptitexam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.Teacher;

public interface StudentService {
    Student findByStudentId(String studentId);

    Page<Student> findByClassrooms(Classroom classroom, Pageable pageable);
}
