package ru.otus.spring.homework04.dao;

import ru.otus.spring.homework04.domain.Quiz;

public interface QuizDao {
    Quiz findQuiz(String resourceName);
}
