package com.web.ptitexam.dto;

import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.Student;

public class ResultDTO {
    private Exam exam;
    private Student student;
    private double score;
    private int correctAnswerCount;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public void setCorrectAnswerCount(int correctAnswerCount) {
        this.correctAnswerCount = correctAnswerCount;
    }

}
