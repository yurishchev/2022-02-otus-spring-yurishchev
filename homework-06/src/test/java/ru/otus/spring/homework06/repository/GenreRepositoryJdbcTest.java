package ru.otus.spring.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework06.domain.Genre;
import ru.otus.spring.homework06.repository.impl.GenreRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Genre Repository Test Suite")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJdbcTest {
    private static final int GENRES_COUNT = 3;
    private static final long EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Fiction";

    @Autowired
    private GenreRepositoryJpa genreRepository;


    @DisplayName("Get all genres in library")
    @Test
    void getAllGenres() {
        List<Genre> actualAuthors = genreRepository.findAll();
        assertThat(actualAuthors).hasSize(GENRES_COUNT);
    }

    @DisplayName("Get genre by id")
    @Test
    void getGenreByIdTest() {
        Optional<Genre> actualGenre = genreRepository.findById(EXISTING_GENRE_ID);
        assertThat(actualGenre).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("name", EXISTING_GENRE_NAME);
    }
}