package ru.otus.spring.homework05.dao;

import ru.otus.spring.homework05.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre getGenreById(Long id);

    List<Genre> getAllGenres();
}
