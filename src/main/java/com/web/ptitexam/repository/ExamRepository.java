package com.web.ptitexam.repository;

import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.Teacher;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository
public interface ExamRepository extends JpaRepository<Exam, String> {
    Exam findByExamId(String examId);

    List<Exam> findAll();

    Page<Exam> findByExamTitle(String ExamTitle, Pageable pageable);

    // Page<Exam> findByStudent(Student student, Pageable pageable);

    Page<Exam> findByTeacher(Teacher teacher, Pageable pageable);

    Page<Exam> findByTeacherAndClassNameContaining(Teacher teacher, String search, Pageable pageable);

    Page<Exam> findByExamId(String examId, String search, Pageable pageable);

    // Page<Exam> findByStudentsStudentId(String studentId, String search, Pageable
    // pageable);

    void deleteById(String id);

    Exam save(Exam exam);
}