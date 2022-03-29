package ru.otus.spring.homework05.domain;

import java.util.List;


public class Author {
    private final String name;
    private final List<Book> books;

    public Author(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

}
