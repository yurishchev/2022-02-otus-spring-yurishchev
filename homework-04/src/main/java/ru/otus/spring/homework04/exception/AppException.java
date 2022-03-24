package ru.otus.spring.homework04.exception;

public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }
}
