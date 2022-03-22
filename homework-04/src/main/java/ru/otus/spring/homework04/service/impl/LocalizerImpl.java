package ru.otus.spring.homework04.service.impl;

import org.springframework.context.MessageSource;
import ru.otus.spring.homework04.service.Localizer;

import java.util.Locale;

public class LocalizerImpl implements Localizer {
    private final MessageSource messageSource;
    private final String localeTag;

    public LocalizerImpl(MessageSource messageSource, String localeTag) {
        this.messageSource = messageSource;
        this.localeTag = localeTag;
    }

    public String getMessage(String messageCode) {
        return getMessage(messageCode, (Object) null);
    }

    public String getMessage(String messageCode, Object... args) {
        return messageSource.getMessage(messageCode, args, Locale.forLanguageTag(localeTag));
    }

}
