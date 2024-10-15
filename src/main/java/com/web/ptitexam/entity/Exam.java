package com.web.ptitexam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam-info")
public class Exam {
    @Column(nullable = false)
    private String examTitle;
    @Column(nullable = false)
    private String examRole;
    @Column(nullable = false)
    private String examAuthor;
    @Column(nullable = false)
    private int examDuration;
    @Column(nullable = false)
    private int questionNumber;
    @Column(nullable = false)
    private int classAssigned;

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getExamRole() {
        return examRole;
    }

    public void setExamRole(String examRole) {
        this.examRole = examRole;
    }

    public String getExamAuthor() {
        return examAuthor;
    }

    public void setExamAuthor(String examAuthor) {
        this.examAuthor = examAuthor;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getClassAssigned() {
        return classAssigned;
    }

    public void setClassAssigned(int classAssigned) {
        this.classAssigned = classAssigned;
    }
}