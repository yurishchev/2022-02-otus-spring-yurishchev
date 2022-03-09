package ru.otus.spring.homework02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.spring.homework02.config.QuizConfig;
import ru.otus.spring.homework02.dao.QuizDaoImpl;
import ru.otus.spring.homework02.domain.Answer;
import ru.otus.spring.homework02.domain.Question;
import ru.otus.spring.homework02.domain.Quiz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class QuizDaoImplTest {
    public static final String TEST_RESOURCE_NAME = "test-questionnaire.csv";
    public static final String TEST_INCORRECT_RESOURCE_NAME = "test-incorrect-questionnaire.csv";

    private QuizDaoImpl quizDao;
    private final QuizConfig quizConfig = new QuizConfig();


    @Test
    void testIncorrectQuiz() {
        quizConfig.setQuizFileName(TEST_INCORRECT_RESOURCE_NAME);
        quizDao = new QuizDaoImpl(quizConfig);
        Quiz quiz = quizDao.findQuiz();

        Assertions.assertEquals(0, quiz.getQuestions().size());
    }

    @Test
    void testNormalQuiz() {
        quizConfig.setQuizFileName(TEST_RESOURCE_NAME);
        quizDao = new QuizDaoImpl(quizConfig);
        Quiz quiz = quizDao.findQuiz();

        Assertions.assertEquals(5, quiz.getQuestions().size());

        Iterator<Question> questionsIterator = quiz.getQuestions().iterator();
        checkQuestionData(questionsIterator.next(), 1, new boolean[]{true, false, true, false, false});
        checkQuestionData(questionsIterator.next(), 2, new boolean[]{true, false});
        checkQuestionData(questionsIterator.next(), 3, new boolean[]{false, false, false});
        checkQuestionData(questionsIterator.next(), 4, new boolean[]{false});
        checkQuestionData(questionsIterator.next(), 5, new boolean[]{true, false, false, false});
    }

    private void checkQuestionData(Question actualQuestion, int expectedQuestionIndex, boolean[] expectedAnswersFlags) {
        Question expectedQuestion = createQuestion(expectedQuestionIndex, expectedAnswersFlags);
        Assertions.assertEquals(expectedQuestion.getText(), actualQuestion.getText());

        List<Answer> actualAnswers = actualQuestion.getAnswers();
        Assertions.assertEquals(expectedQuestion.getAnswers().size(), actualAnswers.size());
        for (Answer actualAnswer : actualAnswers) {
            Assertions.assertEquals(actualAnswer.getText(), actualAnswer.getText());
            Assertions.assertEquals(actualAnswer.isCorrect(), actualAnswer.isCorrect());
            Assertions.assertEquals(actualAnswer, actualAnswer); // for the real test geek :-)
        }
    }

    private Question createQuestion(int expectedIndex, boolean[] answerFlags) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < answerFlags.length; i++) {
            answers.add(new Answer(i, "Answer " + expectedIndex + "-" + (i + 1), answerFlags[i]));
        }
        return new Question("Question " + expectedIndex, answers);
    }

}
