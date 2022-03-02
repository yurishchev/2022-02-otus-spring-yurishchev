package ru.otus.spring.homework01.domain;

import java.util.List;

public class Question {
    private final String text;

    private final List<Answer> answers;

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(text);
        answers.forEach(answer -> sb.append("\n\t").append(answer));
        return sb.toString();
    }
}
