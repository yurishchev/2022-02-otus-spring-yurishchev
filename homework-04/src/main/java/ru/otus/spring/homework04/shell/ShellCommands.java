package ru.otus.spring.homework04.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import static ru.otus.spring.homework04.service.Settings.DEFAULT_LOCALE_TAG;

@ShellComponent
public class ShellCommands {
    private final QuizRunner runner;
    private final Settings settings;
    private final Localizer localizer;

    public ShellCommands(QuizRunner runner, Settings settings, Localizer localizer) {
        this.runner = runner;
        this.settings = settings;
        this.localizer = localizer;
    }

    @ShellMethod("Run Quiz")
    public String run() {
        return runner.run();
    }

    @ShellMethod(value = "Set quiz locale", key = {"set-locale", "sl"})
    public String setLocale(@ShellOption(defaultValue = DEFAULT_LOCALE_TAG, value = {"locale"}) String localeTag) {
        if (!StringUtils.hasLength(localeTag) || settings.getLocales().stream().noneMatch(locale -> locale.equals(localeTag))) {
            return localizer.getMessage("app.unsupported.locale.text", settings.getLocales());
        }

        settings.setSelectedLocaleTag(localeTag);
        return localizer.getMessage("app.locale.text", settings.getSelectedLocaleTag());
    }

    @ShellMethod(value = "Print application settings", key = {"settings", "s"})
    public String getApplicationSettings() {
        return localizer.getMessage("app.locale.text", settings.getSelectedLocaleTag()) + "\n" +
                localizer.getMessage("app.available.locales", settings.getLocales());
    }
}