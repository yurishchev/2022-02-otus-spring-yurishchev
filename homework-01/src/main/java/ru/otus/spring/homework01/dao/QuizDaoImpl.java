package ru.otus.spring.homework01.dao;

import org.springframework.util.StringUtils;
import ru.otus.spring.homework01.domain.Answer;
import ru.otus.spring.homework01.domain.Question;
import ru.otus.spring.homework01.domain.Quiz;

import java.util.*;

public class QuizDaoImpl implements QuizDao {
    private static final String CSV_COLUMNS_DELIMITER = "\\|";

    private final String resourceName;


    public QuizDaoImpl(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public Quiz findQuiz() {
        Set<Question> questions = new LinkedHashSet<>();
        try (Scanner scanner =
                     new Scanner(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(resourceName)))) {
            while (scanner.hasNextLine()) {
                Question question = getQuestionDataFromString(scanner.nextLine());
                if (question != null) {
                    questions.add(question);
                }
            }
        }

        return new Quiz(questions);
    }

    private Question getQuestionDataFromString(String source) {
        Question question = null;
        try (Scanner rowScanner = new Scanner(source)) {
            rowScanner.useDelimiter(CSV_COLUMNS_DELIMITER);

            int index = 0;
            String questionText = null;
            List<Answer> answers = new LinkedList<>();
            while (rowScanner.hasNext()) {
                String data = rowScanner.next().trim();
                if (StringUtils.hasLength(data)) {
                    if (index == 0) {
                        questionText = data;
                    } else {
                        answers.add(new Answer(data));
                    }
                }
                index++;
            }

            if (StringUtils.hasLength(questionText)) {
                question = new Question(questionText, answers);
            }
        }
        return question;
    }
}
