package com.web.ptitexam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.ptitexam.dto.ExamDTO;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.Teacher;

public interface ExamService {

    void createExam(ExamDTO ExamDTO);

    void getCurrentExamInfo();

    ExamDTO getExamList();

    Page<Exam> findByTeacher(Teacher teacher, String search, Pageable pageable);

    Exam findByExamId(String ExamId);

    Page<Exam> findAll();

    void updateExamById(String id, ExamDTO examDTO);
}
