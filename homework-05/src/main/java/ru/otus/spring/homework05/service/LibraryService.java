package ru.otus.spring.homework05.service;

import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;

import java.util.List;

public interface LibraryService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book createBook(String title, Long authorId, Long genreId);

    void updateBook(Long id, String title, Long authorId, Long genreId);

    void deleteBook(Long id);

    List<Author> getAllAuthors();

    List<Genre> getAllGenres();
}
