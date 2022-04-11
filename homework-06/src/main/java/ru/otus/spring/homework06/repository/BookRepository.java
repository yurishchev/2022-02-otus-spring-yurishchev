package ru.otus.spring.homework06.repository;

import ru.otus.spring.homework06.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findBookById(Long id);

    List<Book> findAll();

    Book saveBook(Book book);

    boolean deleteBook(Long id);
}
