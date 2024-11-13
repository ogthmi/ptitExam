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

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam examId;

    @Column(nullable = false)
    private String questionContent;

    @Column(nullable = false)
    private String[] answersContent;

    @Column(nullable = false)
    private int correctAnswerIndex;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Instant questionCreatedAt;

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setExam(Exam examId) {
        this.examId = examId;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public void setAnswersContent(String[] answersContent) {
        this.answersContent = answersContent;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public void setQuestionCreatedAt(Instant questionCreatedAt) {
        this.questionCreatedAt = questionCreatedAt;
    }

    public String getQuestionId() {
        return questionId;
    }

    public Exam getExam() {
        return examId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public String[] getAnswersContent() {
        return answersContent;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public Instant getQuestionCreatedAt() {
        return questionCreatedAt;
    }
}
