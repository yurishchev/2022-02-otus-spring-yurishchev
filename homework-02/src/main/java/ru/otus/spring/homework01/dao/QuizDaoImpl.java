package ru.otus.spring.homework01.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.homework01.domain.Answer;
import ru.otus.spring.homework01.domain.Question;
import ru.otus.spring.homework01.domain.Quiz;

import java.util.*;

@Service
public class QuizDaoImpl implements QuizDao {
    private static final String CSV_COLUMNS_DELIMITER = "\\|";
    private static final String ANSWER_CORRECT_STATUS_PLACEHOLDER = "\\{\\{X}}";

    private final String resourceName;


    public QuizDaoImpl(@Value("${'app.quiz.filename'}") String resourceName) {
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
                Answer answer = null;
                if (index == 0) {
                    questionText = data;
                } else {
                    answer = getAnswerFromString(data);
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

    private Answer getAnswerFromString(String source) {
        String answerText = source.replaceAll(ANSWER_CORRECT_STATUS_PLACEHOLDER, "");
        boolean answerIsCorrect = source.length() > answerText.length();

        if (StringUtils.hasLength(answerText)) {
            return new Answer(answerText, answerIsCorrect);
        }
        return null;
    }
}
