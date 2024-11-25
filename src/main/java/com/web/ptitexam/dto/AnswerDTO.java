package com.web.ptitexam.dto;

public class AnswerDTO {
    private String[] userAnswers;
    private int questionCount;

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public String[] getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(String[] userAnswers) {
        this.userAnswers = userAnswers;
    }

    public AnswerDTO(int questionCount) {
        this.questionCount = questionCount;
        userAnswers = new String[this.questionCount];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = "";
        }
    }

}
