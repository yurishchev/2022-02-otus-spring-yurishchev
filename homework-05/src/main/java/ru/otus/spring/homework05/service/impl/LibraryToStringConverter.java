package ru.otus.spring.homework05.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;
import ru.otus.spring.homework05.service.LibraryConverter;
import ru.otus.spring.homework05.service.Localizer;

@Service
public class LibraryToStringConverter implements LibraryConverter {
    private final Localizer localizer;

    public LibraryToStringConverter(Localizer localizer) {
        this.localizer = localizer;
    }

    @Override
    public String toString(Book book) {
        String authorInfo = book.getAuthor() != null ?
                localizer.getMessage("app.book.author", toString(book.getAuthor())) : "" + book.getAuthorId();
        String genreInfo = book.getGenre() != null ?
                localizer.getMessage("app.book.genre", toString(book.getGenre())) : "" + book.getGenreId();
        return String.join("\t", "" + book.getId(), authorInfo, genreInfo, book.getTitle());
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
