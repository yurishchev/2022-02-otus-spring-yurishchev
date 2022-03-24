package ru.otus.spring.homework04.service;

import ru.otus.spring.homework04.domain.Question;

public interface QuizConverter {
    String toString(Question question);
}
