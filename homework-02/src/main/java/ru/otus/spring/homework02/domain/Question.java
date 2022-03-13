package ru.otus.spring.homework02.domain;

import java.util.List;
import java.util.Optional;

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

    public boolean isCorrectAnswer(int userChoice) {
        Optional<Answer> answer = answers.stream().filter(a -> a.getId() == userChoice).findAny();
        return answer.map(Answer::isCorrect).orElse(false);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(text);
        answers.forEach(answer -> sb.append("\n\t").append(answer));
        return sb.append("\n").toString();
    }
}
