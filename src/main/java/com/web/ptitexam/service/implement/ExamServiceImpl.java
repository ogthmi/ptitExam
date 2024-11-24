package com.web.ptitexam.service.implement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.ptitexam.dto.ExamDTO;
import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.Question;
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

    @Override
    public void createExam(ExamDTO examDTO, Classroom classroom, List<Question> questions) {
        Exam exam = new Exam();
        exam.setExamId(UUID.randomUUID().toString());
        exam.setExamTitle(examDTO.getExamTitle());
        exam.setClassAssignedId(classroom);
        exam.setExamDuration(examDTO.getExamDuration());
        exam.setQuestionCount(examDTO.getQuestionCount());
        exam.setQuestions(questions);
        exam.setExamCreatedAt(LocalDateTime.now());
        examRepository.save(exam);

    }

    @Override
    public void deleteExamById(String examId) {
        examRepository.deleteById(examId);
    }

}