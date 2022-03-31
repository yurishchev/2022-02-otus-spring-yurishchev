package ru.otus.spring.homework05.dao;

import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;

import java.util.List;

public interface LibraryDao {
    Book getBookById(Long id);

    List<Book> getAllBooks();

    long createBook(Book book);

    boolean updateBook(Book book);

    boolean deleteBook(Long id);

    Author getAuthorById(Long id);

    List<Author> getAllAuthors();

    Genre getGenreById(Long id);

    List<Genre> getAllGenres();
}
