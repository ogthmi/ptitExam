package com.web.ptitexam.repository;

import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.Teacher;
import com.web.ptitexam.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByUser(User user);

    Student findByStudentId(String studentId);

    Page<Student> findByClassrooms(Classroom classroom, Pageable pageable);

}
