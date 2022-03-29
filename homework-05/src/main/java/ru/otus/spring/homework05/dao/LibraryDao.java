package ru.otus.spring.homework05.dao;

import ru.otus.spring.homework05.domain.Book;

import java.util.List;

public interface LibraryDao {
    Book getBookById(long id);

    List<Book> getAllBooks();

    void createBook(Book book);

    void updateBook(Book book);

    void deleteBook(Book book);
}
