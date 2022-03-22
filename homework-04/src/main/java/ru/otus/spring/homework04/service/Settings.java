package ru.otus.spring.homework04.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "quiz")
@Component
public class Settings {
    private static final String DEFAULT_LOCALE_TAG = "";
    private static final int DEFAULT_QUESTIONS_NUMBER = 5;
    private static final int DEFAULT_PASS_SCORE = 4;
    private static final String CSV_EXTENSION = ".csv";

    private String localeTag;
    private String fileName;
    private int questionsNumber;
    private int passScore;

    public String getFileName() {
        return StringUtils.hasLength(fileName) ? fileName.substring(0, fileName.indexOf(CSV_EXTENSION)) +
                (StringUtils.hasLength(getLocaleTag()) ? "_" + getLocaleTag() : "") + CSV_EXTENSION : "";
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
