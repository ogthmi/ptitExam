package com.web.ptitexam.service.implement;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.web.ptitexam.dto.QuestionDTO;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.Question;
import com.web.ptitexam.repository.QuestionRepository;
import com.web.ptitexam.service.QuestionService;
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void createQuestion(QuestionDTO questionDTO, int index, String UUID) {
        Question question = new Question();
        question.setQuestionContent(questionDTO.getQuestionContent()[index]);
        question.setAnswersA(questionDTO.getAnswerA()[index]);
        question.setAnswersB(questionDTO.getAnswerB()[index]);
        question.setAnswersC(questionDTO.getAnswerC()[index]);
        question.setAnswersD(questionDTO.getAnswerD()[index]);
        question.setCorrectAnswer(questionDTO.getCorrectAnswers()[index]);

        question.setQuestionId(UUID);

        questionRepository.save(question);
    }

    @Override
    public Question findByQuestionId(String questionId) {
        return questionRepository.findByQuestionId(questionId);
    }

}
