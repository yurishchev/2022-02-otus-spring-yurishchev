package ru.otus.spring.homework02.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework02.dao.QuizDao;
import ru.otus.spring.homework02.domain.Quiz;
import ru.otus.spring.homework02.service.QuizService;

@Service
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
