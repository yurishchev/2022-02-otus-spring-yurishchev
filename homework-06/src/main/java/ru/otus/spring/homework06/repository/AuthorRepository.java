package ru.otus.spring.homework06.repository;

import ru.otus.spring.homework06.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findAuthorById(Long id);

    List<Author> findAll();
}
