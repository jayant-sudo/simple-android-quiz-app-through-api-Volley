package com.example.advancequizapp.Model;

public class QuizData {

    private String questions;
    private boolean answer;

    public QuizData()
    {

    }
    public QuizData(String questions, boolean answer) {
        this.questions = questions;
        this.answer = answer;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

}
