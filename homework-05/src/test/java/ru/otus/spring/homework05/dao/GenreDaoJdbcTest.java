package ru.otus.spring.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework05.dao.impl.GenreDaoJdbc;
import ru.otus.spring.homework05.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Genre Dao Test Suite")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    private static final long EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Fiction";

    @Autowired
    private GenreDaoJdbc genreDao;

    @DisplayName("Get all genres in library")
    @Test
    void getAllGenres() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenres = genreDao.getAllGenres();
        assertThat(actualGenres)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAnyOf(expectedGenre);
    }

    @DisplayName("Get genre by id")
    @Test
    void getGenreByIdTest() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDao.getGenreById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}