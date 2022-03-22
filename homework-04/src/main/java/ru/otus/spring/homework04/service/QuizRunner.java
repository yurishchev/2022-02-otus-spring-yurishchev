package ru.otus.spring.homework04.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework04.domain.Question;
import ru.otus.spring.homework04.domain.Quiz;
import ru.otus.spring.homework04.exception.AppException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizRunner {
    private final QuizService quizService;
    private final IOService ioService;
    private final Settings settings;
    private final QuizConverter converter;
    private final Localizer localizer;

    public QuizRunner(QuizService quizService,
                      IOService ioService,
                      Settings settings,
                      QuizConverter converter,
                      Localizer localizer) {
        this.quizService = quizService;
        this.ioService = ioService;
        this.settings = settings;
        this.converter = converter;
        this.localizer = localizer;
    }

    public void run() {
        Quiz quiz = quizService.loadQuiz();

        ioService.println(localizer.getMessage("app.locale.text", settings.getLocaleTag()));
        String name = ioService.readInputWithLabel(localizer.getMessage("app.enter.name"));

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
                ioService.println(localizer.getMessage("app.input.error"));
            }
        }
    }

    private void printResults(String name, int score) {
        String testResults = (score >= settings.getPassScore()) ?
                localizer.getMessage("app.result.status.success") :
                localizer.getMessage("app.result.status.failure");
        String quizSummaryText =
                localizer.getMessage("app.result.text", name, score, settings.getQuestionsNumber());

        ioService.println(quizSummaryText);
        ioService.println(testResults);
    }
}
