package com.web.ptitexam.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @Column(nullable = false, updatable = false)
    private String examId;

    @Column(nullable = false)
    private String examTitle;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private LocalDateTime examCreatedAt;

    @Column(nullable = false)
    private int examDuration;

    @Column(nullable = false)
    private int questionCount;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classroom classAssigned;

    @ManyToMany
    @JoinTable(name = "exam_questions", joinColumns = @JoinColumn(name = "exam_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questions;

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Classroom getClassAssignedId() {
        return classAssigned;
    }

    public void setClassAssignedId(Classroom classAssigned) {
        this.classAssigned = classAssigned;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public LocalDateTime getExamCreatedAt() {
        return examCreatedAt;
    }

    public void setExamCreatedAt(LocalDateTime examCreatedAt) {
        this.examCreatedAt = examCreatedAt;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

}
