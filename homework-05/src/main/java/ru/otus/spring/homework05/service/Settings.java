package ru.otus.spring.homework05.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@ConfigurationProperties(prefix = "library")
@Component
public class Settings {
    public static final String DEFAULT_LANGUAGE = "en";

    private String selectedLanguage;
    private List<String> languages;

    public String getSelectedLanguage() {
        return StringUtils.hasLength(selectedLanguage) ? selectedLanguage : DEFAULT_LANGUAGE;
    }

    public void setSelectedLanguage(String localeTag) {
        this.selectedLanguage = localeTag;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
}
