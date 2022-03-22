package ru.otus.spring.homework04.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework04.service.Localizer;
import ru.otus.spring.homework04.service.Settings;

import java.util.Locale;

@Service
public class LocalizerImpl implements Localizer {
    private final MessageSource messageSource;
    private final Settings settings;

    public LocalizerImpl(MessageSource messageSource, Settings settings) {
        this.messageSource = messageSource;
        this.settings = settings;
    }

    public String getMessage(String messageCode) {
        return getMessage(messageCode, (Object) null);
    }

    public String getMessage(String messageCode, Object... args) {
        return messageSource.getMessage(messageCode, args, Locale.forLanguageTag(settings.getSelectedLocaleTag()));
    }
}
