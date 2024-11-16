package com.web.ptitexam.dto;

public class QuestionDTO {
    private int questionCount;

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public QuestionDTO(int questionCount) {
        this.questionCount = questionCount;
        questionContent = new String[questionCount];
        answerA = new String[questionCount];
        answerB = new String[questionCount];
        answerC = new String[questionCount];

        answerD = new String[questionCount];
        correctAnswers = new String[questionCount];

    }

    private String[] questionContent;
    private String[] answerA;
    private String[] answerB;

    private String[] answerC;

    private String[] answerD;
    private String[] correctAnswers;

    public String[] getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String[] questionContent) {
        this.questionContent = questionContent;
    }

    public String[] getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String[] answerA) {
        this.answerA = answerA;
    }

    public String[] getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String[] answerB) {
        this.answerB = answerB;
    }

    public String[] getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String[] answerC) {
        this.answerC = answerC;
    }

    public String[] getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String[] answerD) {
        this.answerD = answerD;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

}
