package ru.otus.spring.homework01.domain;

import java.util.Set;

public class Quiz {
    private final Set<Question> questions;

    public Quiz(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        questions.forEach(question -> sb.append("\n").append(question));
        return sb.toString();
    }

}
