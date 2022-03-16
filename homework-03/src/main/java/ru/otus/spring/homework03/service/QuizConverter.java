package ru.otus.spring.homework03.service;

import ru.otus.spring.homework03.domain.Question;

public interface QuizConverter {
    String toString(Question question);
}
