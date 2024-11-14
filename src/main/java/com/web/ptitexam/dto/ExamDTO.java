package com.web.ptitexam.dto;

import java.util.ArrayList;
import java.util.List;

import com.web.ptitexam.entity.Question;

public class ExamDTO {
    private String examTitle;
    private int examDuration;
    private ArrayList<Question> questionList;

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

}