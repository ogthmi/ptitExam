package com.web.ptitexam.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Exam;

public interface ExamRepository
        extends JpaRepository<Exam, String> {
    Exam findByExamId(String examId);

    Exam save(Exam exam);


    Page<Exam> findByClassAssigned(Classroom classroom, Pageable pageable);
}