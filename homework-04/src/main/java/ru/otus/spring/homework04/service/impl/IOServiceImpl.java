package ru.otus.spring.homework04.service.impl;

import ru.otus.spring.homework04.service.IOService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceImpl implements IOService {
    private final Scanner input;
    private final PrintStream output;

    public IOServiceImpl(InputStream inputStream, PrintStream outputStream) {
        this.input = new Scanner(inputStream);
        this.output = outputStream;
    }

    @Override
    public String readInputWithLabel(String label) {
        this.output.print(label);
        return input.nextLine();
    }

    @Override
    public void println(String output) {
        this.output.println(output);
    }
}
