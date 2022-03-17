package ru.otus.spring.homework03.exception;

public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }
}
