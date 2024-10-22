package com.web.ptitexam.service;

import com.web.ptitexam.dto.ExamDTO;

public interface ExamService {

    void createExam(ExamDTO ExamDTO);

    void getCurrentExamInfo();

    ExamDTO getExamList();

}
