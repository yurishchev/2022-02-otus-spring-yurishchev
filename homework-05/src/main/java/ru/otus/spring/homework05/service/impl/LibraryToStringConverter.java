package ru.otus.spring.homework05.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;
import ru.otus.spring.homework05.service.LibraryConverter;

@Service
public class LibraryToStringConverter implements LibraryConverter {

    @Override
    public String toString(Book book) {
        return String.join("\t", "" + book.getId(), "" + book.getAuthorId(), "" + book.getGenreId(), book.getTitle());
    }

    @Override
    public String toString(Author author) {
        return String.join("\t", "" + author.getId(), author.getFirstName() + " " + author.getLastName());
    }

    @Override
    public String toString(Genre genre) {
        return String.join("\t", "" + genre.getId(), genre.getName());
    }
}
