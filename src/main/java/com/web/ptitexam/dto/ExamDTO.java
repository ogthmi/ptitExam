package com.web.ptitexam.dto;

import java.util.ArrayList;

public class ExamDTO {

    private String examTitle;

    private String examRole;

    private String examAuthor;

    private int examDuration;

    private int questionNumber;

    private int classAssigned;

    private ArrayList<String> questionIdList;

    public ArrayList<String> getQuestionIdList() {
        return questionIdList;
    }

    public void setQuestionIdList(ArrayList<String> questionIdList) {
        this.questionIdList = questionIdList;
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
