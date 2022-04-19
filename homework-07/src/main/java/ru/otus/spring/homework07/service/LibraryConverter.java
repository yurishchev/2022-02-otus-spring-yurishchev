package ru.otus.spring.homework07.service;

import ru.otus.spring.homework07.domain.Author;
import ru.otus.spring.homework07.domain.Book;
import ru.otus.spring.homework07.domain.Comment;
import ru.otus.spring.homework07.domain.Genre;

public interface LibraryConverter {
    String toString(Book book);

    String toString(Author author);

    String toString(Genre genre);

    String toString(Comment comment);
}
