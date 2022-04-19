package ru.otus.spring.homework07.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework07.domain.Author;
import ru.otus.spring.homework07.domain.Book;
import ru.otus.spring.homework07.domain.Comment;
import ru.otus.spring.homework07.domain.Genre;
import ru.otus.spring.homework07.service.LibraryConverter;
import ru.otus.spring.homework07.service.Localizer;

@Service
public class LibraryToStringConverter implements LibraryConverter {
    private final Localizer localizer;

    public LibraryToStringConverter(Localizer localizer) {
        this.localizer = localizer;
    }

    @Override
    public String toString(Book book) {
        String authorInfo = localizer.getMessage("app.book.author", toString(book.getAuthor()));
        StringBuilder genres = new StringBuilder();
        for (Genre genre : book.getGenres()) {
            genres.append(toString(genre)).append("; ");
        }
        String genresInfo = localizer.getMessage("app.book.genre", genres);
        return String.join("\t", "" + book.getId(), authorInfo, genresInfo, book.getTitle());
    }

    @Override
    public String toString(Author author) {
        return String.join("  ", "ID=" + author.getId(), author.getFirstName() + " " + author.getLastName());
    }

    @Override
    public String toString(Genre genre) {
        return String.join("  ", "ID=" + genre.getId(), genre.getName());
    }

    @Override
    public String toString(Comment comment) {
        return String.join("  ", "ID=" + comment.getId(), comment.getText(), comment.getFrom());
    }
}
