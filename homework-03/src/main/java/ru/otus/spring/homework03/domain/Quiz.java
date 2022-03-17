package ru.otus.spring.homework03.domain;

import java.util.Set;

public class Quiz {
    private final Set<Question> questions;

    public Quiz(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Question> getQuestions() {
        return questions;
    }
}
