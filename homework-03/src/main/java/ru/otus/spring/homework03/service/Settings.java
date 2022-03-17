package ru.otus.spring.homework03.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import ru.otus.spring.homework03.dao.impl.QuizDaoCSV;

@ConfigurationProperties(prefix = "quiz")
public class Settings {
    public static final String DEFAULT_LOCALE_TAG = "";
    public static final int DEFAULT_QUESTIONS_NUMBER = 5;
    public static final int DEFAULT_PASS_SCORE = 4;

    private String localeTag;
    private String fileName;
    private int questionsNumber;
    private int passScore;

    public String getFileName() {
        // TODO it also makes sense to pass locale directly to QuizDao findQuiz() to make it more explicit
        return StringUtils.hasLength(fileName) ? fileName +
                (StringUtils.hasLength(getLocaleTag()) ? "_" + getLocaleTag() : "") : "";
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getQuestionsNumber() {
        return questionsNumber > 0 ? questionsNumber : DEFAULT_QUESTIONS_NUMBER;
    }

    public void setQuestionsNumber(int questionsNumber) {
        this.questionsNumber = questionsNumber;
    }

    public int getPassScore() {
        return passScore > 0 ? passScore : DEFAULT_PASS_SCORE;
    }

    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }

    public String getLocaleTag() {
        return StringUtils.hasLength(localeTag) ? localeTag : DEFAULT_LOCALE_TAG;
    }

    public void setLocaleTag(String localeTag) {
        this.localeTag = localeTag;
    }
}
