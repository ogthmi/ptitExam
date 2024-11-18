package com.web.ptitexam.dto;

public class AnswerDTO {
    private String[] userAnswers;

    public String[] getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(String[] userAnswers) {
        this.userAnswers = userAnswers;
    }

    public AnswerDTO(int questionCount) {
        userAnswers = new String[questionCount];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = "";
        }
    }

}
