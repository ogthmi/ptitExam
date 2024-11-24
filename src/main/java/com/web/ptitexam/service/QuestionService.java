package com.web.ptitexam.service;

import com.web.ptitexam.dto.QuestionDTO;
import com.web.ptitexam.entity.Question;

public interface QuestionService {
    void createQuestion(QuestionDTO questionDTO, int index, String UUID);

    Question findByQuestionId(String questionId);
}
