package com.web.ptitexam.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @Column(nullable = false)
    private String questionId;

    @Column(nullable = false)
    private String questionContent;

    @Column(nullable = false)
    private String answersA;
    @Column(nullable = false)
    private String answersB;
    @Column(nullable = false)
    private String answersC;
    @Column(nullable = false)
    private String answersD;

    @Column(nullable = false)
    private String correctAnswer;

    public String getAnswersA() {
        return answersA;
    }

    public void setAnswersA(String answersA) {
        this.answersA = answersA;
    }

    public String getAnswersB() {
        return answersB;
    }

    public void setAnswersB(String answersB) {
        this.answersB = answersB;
    }

    public String getAnswersC() {
        return answersC;
    }

    public void setAnswersC(String answersC) {
        this.answersC = answersC;
    }

    public String getAnswersD() {
        return answersD;
    }

    public void setAnswersD(String answersD) {
        this.answersD = answersD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Instant questionCreatedAt;

    public Question() {
        questionContent = "";
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public void setQuestionCreatedAt(Instant questionCreatedAt) {
        this.questionCreatedAt = questionCreatedAt;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    Instant getQuestionCreatedAt() {
        return questionCreatedAt;
    }
}
