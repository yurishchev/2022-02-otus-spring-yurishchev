package ru.otus.spring.homework01;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework01.domain.Quiz;
import ru.otus.spring.homework01.service.QuizConsole;
import ru.otus.spring.homework01.service.QuizConsoleImpl;
import ru.otus.spring.homework01.service.QuizService;

@Service
public class QuizRunner {
    private final QuizService quizService;

    public QuizRunner(QuizService quizService) {
        this.quizService = quizService;
    }

    public void run() {
        Quiz quiz = quizService.loadQuiz();

        // Standard classes shouldn't be present in spring context, therefore we create directly console object with PrintWriter passed to its constructor
        QuizConsole quizConsole = new QuizConsoleImpl(System.out);
        quizConsole.print(quiz);
    }
}
