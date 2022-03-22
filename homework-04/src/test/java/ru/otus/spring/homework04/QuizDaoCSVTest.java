package ru.otus.spring.homework04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.homework04.dao.impl.QuizDaoCSV;
import ru.otus.spring.homework04.domain.Answer;
import ru.otus.spring.homework04.domain.Question;
import ru.otus.spring.homework04.domain.Quiz;
import ru.otus.spring.homework04.service.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuizDaoCSVTest {
    public static final String TEST_RESOURCE_NAME = "test-questionnaire.csv";
    public static final String TEST_INCORRECT_RESOURCE_NAME = "test-incorrect-questionnaire.csv";

    private QuizDaoCSV quizDao;
    private Settings settings;

    @BeforeEach
    public void setup() {
        settings = mock(Settings.class);
    }

    @Test
    void testIncorrectQuiz() {
        when(settings.getFileName()).thenReturn(TEST_INCORRECT_RESOURCE_NAME);

        quizDao = new QuizDaoCSV();
        Quiz quiz = quizDao.findQuiz(settings.getFileName());

        assertThat(quiz.getQuestions().size()).isEqualTo(0);
    }

    @Test
    void testNormalQuiz() {
        when(settings.getFileName()).thenReturn(TEST_RESOURCE_NAME);
        Question expectedQuestion1 = createQuestion(1, new boolean[]{true, false, true, false, false});
        Question expectedQuestion2 = createQuestion(2, new boolean[]{true, false});
        Question expectedQuestion3 = createQuestion(3, new boolean[]{false, false, false});
        Question expectedQuestion4 = createQuestion(4, new boolean[]{false});
        Question expectedQuestion5 = createQuestion(5, new boolean[]{true, false, false, false});

        quizDao = new QuizDaoCSV();
        Quiz quiz = quizDao.findQuiz(settings.getFileName());
        Iterator<Question> questionsIterator = quiz.getQuestions().iterator();

        assertThat(quiz.getQuestions().size()).isEqualTo(5);
        assertThat(questionsIterator.next()).usingRecursiveComparison().isEqualTo(expectedQuestion1);
        assertThat(questionsIterator.next()).usingRecursiveComparison().isEqualTo(expectedQuestion2);
        assertThat(questionsIterator.next()).usingRecursiveComparison().isEqualTo(expectedQuestion3);
        assertThat(questionsIterator.next()).usingRecursiveComparison().isEqualTo(expectedQuestion4);
        assertThat(questionsIterator.next()).usingRecursiveComparison().isEqualTo(expectedQuestion5);
    }

    private Question createQuestion(int expectedIndex, boolean[] answerFlags) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 1; i <= answerFlags.length; i++) {
            answers.add(new Answer(i, "Answer " + expectedIndex + "-" + i, answerFlags[i - 1]));
        }
        return new Question("Question " + expectedIndex, answers);
    }

}
