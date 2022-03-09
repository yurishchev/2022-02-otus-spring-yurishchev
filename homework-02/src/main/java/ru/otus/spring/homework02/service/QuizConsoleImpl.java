package ru.otus.spring.homework02.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class QuizConsoleImpl implements QuizConsole {

    private final PrintStream output;
    private final Scanner input;

    public QuizConsoleImpl() {
        this.output = System.out;
        this.input = new Scanner(System.in);
    }

    @Override
    public String readInputWithLabel(String label) {
        print(label);
        return input.nextLine();
    }

    @Override
    public void println(String output) {
        this.output.println(output);
    }

    public void print(String output) {
        this.output.print(output);
    }
}
