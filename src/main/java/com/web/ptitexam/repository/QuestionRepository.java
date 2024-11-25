package com.web.ptitexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ptitexam.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, String> {

    @SuppressWarnings("unchecked")
    Question save(Question question);

    Question findByQuestionId(String questionId);
}