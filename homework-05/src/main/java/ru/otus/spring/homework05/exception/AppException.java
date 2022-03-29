package ru.otus.spring.homework05.exception;

public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }
}
