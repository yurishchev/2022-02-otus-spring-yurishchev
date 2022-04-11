package ru.otus.spring.homework06.repository;

import ru.otus.spring.homework06.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> findGenreById(Long id);

    List<Genre> findAll();
}
