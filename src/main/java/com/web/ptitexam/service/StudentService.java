package com.web.ptitexam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Student;
@Service
public interface StudentService {
    Student findByStudentId(String studentId);

    Page<Student> findByClassrooms(Classroom classroom, String search, Pageable pageable);
}
