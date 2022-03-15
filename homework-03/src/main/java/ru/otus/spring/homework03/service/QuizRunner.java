package ru.otus.spring.homework03.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework03.domain.Question;
import ru.otus.spring.homework03.domain.Quiz;
import ru.otus.spring.homework03.exception.AppException;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizRunner {
    private final QuizService quizService;
    private final IOService ioService;
    private final Settings settings;
    private final StringConverter converter;
    private final MessageSource messageSource;

    public QuizRunner(QuizService quizService,
                      IOService ioService,
                      Settings settings,
                      StringConverter converter,
                      MessageSource messageSource) {
        this.quizService = quizService;
        this.ioService = ioService;
        this.settings = settings;
        this.converter = converter;
        this.messageSource = messageSource;
    }

    public void run() {
        Quiz quiz = quizService.loadQuiz();

        ioService.println(getLocalizedMessage("app.locale.text", new Object[] {settings.getLocaleTag()}));
        String name = ioService.readInputWithLabel(getLocalizedMessage("app.enter.name"));

        printResults(name, calculateQuizScore(quiz));
    }

    private int calculateQuizScore(Quiz quiz) {
        int score = 0;
        Set<Question> questions = quiz.getQuestions().stream().limit(settings.getQuestionsNumber()).collect(Collectors.toSet());
        for (Question question : questions) {
            score += isUserAnswerCorrectForQuestion(question) ? 1 : 0;
        }
        return score;
    }

    private boolean isUserAnswerCorrectForQuestion(Question question) {
        int userAnswer = readUserAnswer(question);
        return question.isCorrectAnswer(userAnswer);
    }

    private int readUserAnswer(Question question) {
        while (true) {
            try {
                int answerId = Integer.parseInt(ioService.readInputWithLabel(converter.toString(question) + "> "));
                if (answerId < 1 || answerId > question.getAnswers().size()) {
                    throw new AppException("User answer is out of range of valid numbers!");
                }
                return answerId;
            } catch (NumberFormatException | AppException e) {
                ioService.println(getLocalizedMessage("app.input.error"));
            }
        }
    }

    private void printResults(String name, int score) {
        String testResults = (score >= settings.getPassScore()) ?
                getLocalizedMessage("app.result.status.success") :
                getLocalizedMessage("app.result.status.failure");
        String quizSummaryText =
                getLocalizedMessage("app.result.text", new Object[]{name, score, settings.getQuestionsNumber()});

        ioService.println(quizSummaryText);
        ioService.println(testResults);
    }

    private String getLocalizedMessage(String code) {
        return getLocalizedMessage(code, null);
    }

    private String getLocalizedMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, Locale.forLanguageTag(settings.getLocaleTag()));
    }
}
