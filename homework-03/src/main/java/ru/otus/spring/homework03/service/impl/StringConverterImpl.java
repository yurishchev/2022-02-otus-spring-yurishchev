package ru.otus.spring.homework03.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework03.domain.Answer;
import ru.otus.spring.homework03.domain.Question;
import ru.otus.spring.homework03.service.StringConverter;

@Service
public class StringConverterImpl implements StringConverter {

    @Override
    public String toString(Question question) {
        StringBuilder sb = new StringBuilder(question.getText());
        question.getAnswers().forEach(answer -> sb.append("\n\t").append(toString(answer)));
        return sb.append("\n").toString();
    }

    private String toString(Answer answer) {
        return answer.getId() + ". " + answer.getText();
    }
}
