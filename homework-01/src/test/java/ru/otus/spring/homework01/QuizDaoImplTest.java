package ru.otus.spring.homework01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.spring.homework01.dao.QuizDaoImpl;
import ru.otus.spring.homework01.domain.Quiz;

class QuizDaoImplTest {
    public static final String TEST_RESOURCE_NAME = "test-questionnaire.csv";
    public static final String TEST_INCORRECT_RESOURCE_NAME = "test-incorrect-questionnaire.csv";
    private QuizDaoImpl dao;

    @Test
    void testNormalQuiz() {
        dao = new QuizDaoImpl(TEST_RESOURCE_NAME);
        Quiz quiz = dao.findQuiz();

        Assertions.assertEquals(6, quiz.getQuestions().size());
        //Assertions.assertEquals(6, quiz.getQuestions(0).get);
    }

    @Test
    void testIncorrectQuiz() {
        dao = new QuizDaoImpl(TEST_INCORRECT_RESOURCE_NAME);
        Quiz quiz = dao.findQuiz();

        Assertions.assertEquals(1, quiz.getQuestions().size());
    }

}
