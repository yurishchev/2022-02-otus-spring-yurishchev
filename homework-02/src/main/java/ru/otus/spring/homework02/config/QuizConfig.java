package ru.otus.spring.homework02.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Configuration
public class QuizConfig {
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
