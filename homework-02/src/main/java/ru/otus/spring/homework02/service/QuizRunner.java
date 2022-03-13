package ru.otus.spring.homework02.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework02.exception.AppException;
import ru.otus.spring.homework02.domain.Question;
import ru.otus.spring.homework02.domain.Quiz;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizRunner {
    private final QuizService quizService;
    private final IOService ioService;
    private final Settings settings;

    public QuizRunner(QuizService quizService, IOService ioService, Settings settings) {
        this.quizService = quizService;
        this.ioService = ioService;
        this.settings = settings;
    }

    public void run() {
        Quiz quiz = quizService.loadQuiz();

        String name = ioService.readInputWithLabel("Enter your name: ");

        printResults(name, calculateQuizScore(quiz));
    }

    private int calculateQuizScore(Quiz quiz) {
        int score = 0;
        Set<Question> questions = quiz.getQuestions().stream().limit(settings.getNumberOfQuestions()).collect(Collectors.toSet());
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
                int answerId = Integer.parseInt(ioService.readInputWithLabel(question.toString() + "> "));
                if (answerId < 1 || answerId > question.getAnswers().size()) {
                    throw new AppException("User answer is out of range of valid numbers!");
                }
                return answerId;
            } catch (NumberFormatException | AppException e) {
                ioService.println("Invalid answer! Please try again.");
            }
        }
    }

    private void printResults(String name, int score) {
        String testResults = (score >= settings.getPassScore()) ? "You passed test!" : "You failed, please try again.";
        ioService.println("Dear " + name + "! Your score for this quiz is: " + score + " out of " + settings.getNumberOfQuestions());
        ioService.println(testResults);
    }
}
