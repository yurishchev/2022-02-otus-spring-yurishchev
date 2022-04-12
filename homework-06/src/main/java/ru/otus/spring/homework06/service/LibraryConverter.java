package ru.otus.spring.homework06.service;

import ru.otus.spring.homework06.domain.Author;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.domain.Comment;
import ru.otus.spring.homework06.domain.Genre;

public interface LibraryConverter {
    String toString(Book book);

    String toString(Author author);

    String toString(Genre genre);

    String toString(Comment comment);
}
