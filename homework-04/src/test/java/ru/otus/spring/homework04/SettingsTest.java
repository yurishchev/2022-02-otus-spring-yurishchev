package ru.otus.spring.homework04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.homework04.service.Settings;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.homework04.service.Settings.DEFAULT_LOCALE_TAG;


@DisplayName("Homework 04 - Settings Integration Tests")
@SpringBootTest
public class SettingsTest {
    private static final String EXPECTED_EN_LOCALE = DEFAULT_LOCALE_TAG;
    private static final String EXPECTED_RU_LOCALE = "ru-RU";
    private static final String EXPECTED_FILE_NAME = "test-questionnaire.csv";

    @Autowired
    private Settings settings;


    @DisplayName("Check default application settings")
    @Test
    void shouldContainAllDefaultSettings() {
        assertThat(settings.getFileName()).isEqualTo(EXPECTED_FILE_NAME);
        assertThat(settings.getQuestionsNumber()).isEqualTo(5);
        assertThat(settings.getPassScore()).isEqualTo(4);
        assertThat(settings.getLocales()).isEqualTo(Arrays.asList(EXPECTED_EN_LOCALE, EXPECTED_RU_LOCALE));
        assertThat(settings.getSelectedLocaleTag()).isEqualTo(EXPECTED_EN_LOCALE);
    }

    @DisplayName("Check settings after selecting english locale")
    @Test
    void checkSettingsAfterSelectingEnglishLocale() {
        settings.setSelectedLocaleTag(EXPECTED_EN_LOCALE);

        assertThat(settings.getSelectedLocaleTag()).isEqualTo(EXPECTED_EN_LOCALE);
        assertThat(settings.getFileName()).isEqualTo(EXPECTED_FILE_NAME);
    }

    @DisplayName("Check settings after selecting russian locale")
    @Test
    void checkSettingsAfterSelectingRussianLocale() {
        settings.setSelectedLocaleTag(EXPECTED_RU_LOCALE);

        assertThat(settings.getSelectedLocaleTag()).isEqualTo(EXPECTED_RU_LOCALE);
        assertThat(settings.getFileName()).isEqualTo("test-questionnaire_ru-RU.csv");
    }
}
