package ru.otus.spring.homework02.domain;

public class Answer {
    private final int id;
    private final String text;
    private final boolean correct;

    public Answer(int id, String text, boolean correct) {
        this.id = id;
        this.text = text;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return id + ". " + text;
    }
}
