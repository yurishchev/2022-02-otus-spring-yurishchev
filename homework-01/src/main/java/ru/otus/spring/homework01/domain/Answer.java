package ru.otus.spring.homework01.domain;

public class Answer {
    private final String text;

    public Answer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
