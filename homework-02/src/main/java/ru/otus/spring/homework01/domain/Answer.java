package ru.otus.spring.homework01.domain;

import java.util.Objects;

public class Answer {
    private final String text;
    private final boolean correctStatus;

    public Answer(String text, boolean correctStatus) {
        this.text = text;
        this.correctStatus = correctStatus;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correctStatus;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return correctStatus == answer.correctStatus && text.equals(answer.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
