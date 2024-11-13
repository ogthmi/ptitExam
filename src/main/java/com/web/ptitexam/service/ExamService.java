package com.web.ptitexam.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Exam;

@Service
public interface ExamService {
    Exam findByExamId(String examId);

    Page<Exam> findByClassAssigned(Classroom classroom, String search, Pageable pageable);
}