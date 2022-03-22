package ru.otus.spring.homework04.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@ConfigurationProperties(prefix = "quiz")
@Component
public class Settings {
    private static final String DEFAULT_LOCALE_TAG = "en-EN";
    private static final int DEFAULT_QUESTIONS_NUMBER = 5;
    private static final int DEFAULT_PASS_SCORE = 4;
    private static final String CSV_EXTENSION = ".csv";

    private String fileName;
    private int questionsNumber;
    private int passScore;
    private List<String> locales;
    private String selectedLocaleTag;

    public String getFileName() {
        String selectedLocale = getSelectedLocaleTag().replaceAll(DEFAULT_LOCALE_TAG, "");
        String localeSuffix = StringUtils.hasLength(selectedLocale) ? "_" + selectedLocale : "";
        return StringUtils.hasLength(fileName) ?
                fileName.substring(0, fileName.indexOf(CSV_EXTENSION)) + localeSuffix + CSV_EXTENSION
                : "";
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

    public String getSelectedLocaleTag() {
        return StringUtils.hasLength(selectedLocaleTag) ? selectedLocaleTag : DEFAULT_LOCALE_TAG;
    }

    public void setSelectedLocaleTag(String localeTag) {
        this.selectedLocaleTag = localeTag;
    }

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }
}
