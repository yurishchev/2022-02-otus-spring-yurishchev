package ru.otus.spring.homework01.service;

import ru.otus.spring.homework01.dao.QuizDao;
import ru.otus.spring.homework01.domain.Quiz;

public class QuizServiceImpl implements QuizService {
    private final QuizDao dao;

    public QuizServiceImpl(QuizDao dao) {
        this.dao = dao;
    }

    @Override
    public Quiz loadQuiz() {
        return dao.findQuiz();
    }
}
