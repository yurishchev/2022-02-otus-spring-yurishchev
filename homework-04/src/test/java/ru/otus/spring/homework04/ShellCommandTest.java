package ru.otus.spring.homework04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.homework04.service.IOService;
import ru.otus.spring.homework04.shell.ShellCommands;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@DisplayName("Homework 04 - Shell Command Integration Tests")
@SpringBootTest
public class ShellCommandTest {

    @Autowired
    private ShellCommands shellCommands;

    @MockBean
    private IOService ioService;


    @DisplayName("Check valid setLocale command in shell")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void checkValidShellSetLocaleCommand() {
        assertThat(shellCommands.setLocale("ru-RU")).isEqualTo("Quiz Locale: ru-RU");
    }

    @DisplayName("Check invalid setLocale command in shell")
    @Test
    void checkInvalidShellSetLocaleCommand() {
        assertThat(shellCommands.setLocale("dummy")).isEqualTo("" +
                "Unsupported locale! Available options: [en-US, ru-RU]");
    }

    @DisplayName("Check application settings command in shell")
    @Test
    void checkApplicationSettingsCommand() {
        assertThat(shellCommands.getApplicationSettings()).isEqualTo("" +
                "Quiz Locale: en-US\nAvailable locales: [en-US, ru-RU]");
    }

    @DisplayName("Check failed quiz")
    @Test
    void checkFailedQuiz() {
        when(ioService.readInputWithLabel(any(String.class))).thenReturn("Test User", "1");

        assertThat(shellCommands.run()).isEqualTo(
                "Dear Test User! Your score for this quiz is: 3 out of 5\nYou failed, please try again.");
    }

    @DisplayName("Check passed quiz")
    @Test
    void checkPassedQuiz() {
        when(ioService.readInputWithLabel(any(String.class))).thenReturn("Test User", "1", "1", "3", "1", "2");

        assertThat(shellCommands.run()).isEqualTo(
                "Dear Test User! Your score for this quiz is: 5 out of 5\nYou passed test!");
    }

}
