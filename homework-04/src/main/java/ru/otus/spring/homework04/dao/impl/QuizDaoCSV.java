package ru.otus.spring.homework04.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.homework04.dao.QuizDao;
import ru.otus.spring.homework04.domain.Answer;
import ru.otus.spring.homework04.domain.Question;
import ru.otus.spring.homework04.domain.Quiz;
import ru.otus.spring.homework04.exception.AppException;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class QuizDaoCSV implements QuizDao {
    private static final String CSV_COLUMNS_DELIMITER = "\\|";
    private static final String ANSWER_CORRECT_STATUS_PLACEHOLDER = "\\{\\{X}}";

    @Override
    public Quiz findQuiz(String resourceName) {
        Set<Question> questions = new LinkedHashSet<>();
        try (Scanner scanner =
                     new Scanner(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(resourceName)),
                             StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                Question question = getQuestionDataFromString(scanner.nextLine());
                if (question != null) {
                    questions.add(question);
                }
            }
        } catch (NullPointerException npe) {
            throw new AppException("Can't open file '" + resourceName + "'!");
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
                Answer answer = null;
                if (index == 0) {
                    questionText = data;
                } else {
                    answer = getAnswerFromString(index, data);
                }

                if (!StringUtils.hasLength(questionText)) {
                    return null; // stop processing corrupted question data
                }
                if (answer != null) {
                    answers.add(answer);
                }

                index++;
            }

            if (isValidQuestionData(questionText, answers)) {
                question = new Question(questionText, answers);
            }
        }
        return question;
    }

    private boolean isValidQuestionData(String text, List<Answer> answers) {
        return StringUtils.hasLength(text) && answers.size() > 0;
    }

    private Answer getAnswerFromString(int index, String source) {
        String answerText = source.replaceAll(ANSWER_CORRECT_STATUS_PLACEHOLDER, "");
        boolean answerIsCorrect = source.length() > answerText.length();

        if (StringUtils.hasLength(answerText) || ANSWER_CORRECT_STATUS_PLACEHOLDER.equals(source)) {
            return new Answer(index, answerText, answerIsCorrect);
        }
        return null;
    }
}
