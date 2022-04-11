package ru.otus.spring.homework05.dao;

import ru.otus.spring.homework05.domain.Book;

import java.util.List;

public interface BookDao {
    Book getBookById(Long id);

    List<Book> getAllBooks();

    long createBook(Book book);

    boolean updateBook(Book book);

    boolean deleteBook(Long id);
}
