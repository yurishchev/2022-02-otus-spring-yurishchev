package ru.otus.spring.homework02;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework02.config.QuizConfig;
import ru.otus.spring.homework02.domain.Question;
import ru.otus.spring.homework02.domain.Quiz;
import ru.otus.spring.homework02.service.QuizConsole;
import ru.otus.spring.homework02.service.QuizService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizRunner {
    private final QuizService quizService;
    private final QuizConsole quizConsole;
    private final QuizConfig quizConfig;

    public QuizRunner(QuizService quizService, QuizConsole quizConsole, QuizConfig quizConfig) {
        this.quizService = quizService;
        this.quizConsole = quizConsole;
        this.quizConfig = quizConfig;
    }

    public void run() {
        Quiz quiz = quizService.loadQuiz();

        String name = quizConsole.readInputWithLabel("Enter your name: ");

        printResults(name, calculateQuizScore(quiz));
    }

    private int calculateQuizScore(Quiz quiz) {
        int score = 0;
        Set<Question> questions = quiz.getQuestions().stream().limit(quizConfig.getNumberOfQuestions()).collect(Collectors.toSet());
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
                int answerId = Integer.parseInt(quizConsole.readInputWithLabel(question.toString() + "> "));
                if (answerId < 1 || answerId > question.getAnswers().size()) {
                    throw new QuizException("User answer is out of range of valid numbers!");
                }
                return answerId;
            } catch (NumberFormatException | QuizException e) {
                quizConsole.println("Invalid answer! Please try again.");
            }
        }
    }

    private void printResults(String name, int score) {
        String testResults = (score >= quizConfig.getPassScore()) ? "You passed test!" : "You failed, please try again.";
        quizConsole.println("Dear " + name + "! Your score for this quiz is: " + score + " out of " + quizConfig.getNumberOfQuestions());
        quizConsole.println(testResults);
    }
}
