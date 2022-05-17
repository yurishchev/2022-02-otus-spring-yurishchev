package ru.otus.spring.homework07.service;

public interface Localizer {
    String getMessage(String messageCode);

    String getMessage(String messageCode, Object... args);
}
