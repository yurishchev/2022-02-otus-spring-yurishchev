package ru.otus.spring.homework02.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Settings {
    private String quizFileName;
    private int numberOfQuestions;
    private int passScore;

    public String getQuizFileName() {
        return quizFileName;
    }

    @Value("${app.quiz.filename}")
    public void setQuizFileName(String quizFileName) {
        this.quizFileName = quizFileName;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    @Value("${app.quiz.questions.number}")
    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getPassScore() {
        return passScore;
    }

    @Value("${app.quiz.pass.score}")
    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }
}
