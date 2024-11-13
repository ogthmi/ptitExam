package com.web.ptitexam.service.implement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.repository.ExamRepository;
import com.web.ptitexam.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Exam findByExamId(String examId) {
        return examRepository.findByExamId(examId);
    }

    @Override
    public Page<Exam> findByClassAssigned(Classroom classroom, String search, Pageable pageable) {
        /// TODO: sẽ làm sort sau(quá lười)
        return examRepository.findByClassAssigned(classroom, pageable);
    }

}