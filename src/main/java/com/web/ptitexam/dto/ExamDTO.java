package com.web.ptitexam.dto;

import java.util.ArrayList;

import com.web.ptitexam.entity.Teacher;

public class ExamDTO {

    private String examTitle;

    private String examRole;

    private int examDuration;

    private int questionNumber;

    private int classAssigned;

    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

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
