package ru.otus.spring.homework01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.spring.homework01.dao.QuizDaoImpl;
import ru.otus.spring.homework01.domain.Answer;
import ru.otus.spring.homework01.domain.Question;
import ru.otus.spring.homework01.domain.Quiz;

import java.util.Iterator;
import java.util.List;

class QuizDaoImplTest {
    public static final String TEST_RESOURCE_NAME = "test-questionnaire.csv";
    public static final String TEST_INCORRECT_RESOURCE_NAME = "test-incorrect-questionnaire.csv";

    private QuizDaoImpl quizDao;


    @Test
    void testIncorrectQuiz() {
        quizDao = new QuizDaoImpl(TEST_INCORRECT_RESOURCE_NAME);
        Quiz quiz = quizDao.findQuiz();

        Assertions.assertEquals(1, quiz.getQuestions().size());

        Question question = quiz.getQuestions().iterator().next();
        Assertions.assertEquals("fake question without answers", question.getText());
        Assertions.assertEquals(0, question.getAnswers().size());
    }

    @Test
    void testNormalQuiz() {
        quizDao = new QuizDaoImpl(TEST_RESOURCE_NAME);
        Quiz quiz = quizDao.findQuiz();

        Assertions.assertEquals(5, quiz.getQuestions().size());

        Iterator<Question> questionsIterator = quiz.getQuestions().iterator();
        checkQuestionData(questionsIterator.next(), 1, 5);
        checkQuestionData(questionsIterator.next(), 2, 2);
        checkQuestionData(questionsIterator.next(), 3, 3);
        checkQuestionData(questionsIterator.next(), 4, 1);
        checkQuestionData(questionsIterator.next(), 5, 4);
    }

    private void checkQuestionData(Question question, int expectedIndex, int expectedNumberOfAnswers) {
        Assertions.assertEquals("Question " + expectedIndex, question.getText());
        List<Answer> answers = question.getAnswers();
        Assertions.assertEquals(expectedNumberOfAnswers, answers.size());
        for (int i = 0; i < answers.size(); i++) {
            Assertions.assertEquals("Answer " + expectedIndex + "-" + (i + 1), answers.get(i).getText());
        }
    }

}
