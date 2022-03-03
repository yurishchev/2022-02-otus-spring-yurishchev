package ru.otus.spring.homework01.service;

import ru.otus.spring.homework01.domain.Quiz;

import java.io.PrintStream;

public class QuizConsoleImpl implements QuizConsole {

    private final PrintStream out;

    public QuizConsoleImpl(PrintStream out) {
        this.out = out;
    }

    @Override
    public void print(Quiz quiz) {
        out.println(quiz);
    }
}
