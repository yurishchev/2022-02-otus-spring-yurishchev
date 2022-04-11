package ru.otus.spring.homework05.dao;

import ru.otus.spring.homework05.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getAuthorById(Long id);

    List<Author> getAllAuthors();
}
