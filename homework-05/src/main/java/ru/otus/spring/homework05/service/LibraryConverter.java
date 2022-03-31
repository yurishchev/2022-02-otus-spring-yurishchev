package ru.otus.spring.homework05.service;

import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;

public interface LibraryConverter {
    String toString(Book book);

    String toString(Author author);

    String toString(Genre genre);
}
