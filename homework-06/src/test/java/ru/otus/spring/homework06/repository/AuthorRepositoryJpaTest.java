package ru.otus.spring.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework06.domain.Author;
import ru.otus.spring.homework06.repository.impl.AuthorRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Author Repository Test Suite")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {
    private static final int AUTHORS_COUNT = 6;

    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_FIRSTNAME = "Lev";
    private static final String EXISTING_AUTHOR_LASTNAME = "Tolstoy";

    @Autowired
    private AuthorRepositoryJpa authorRepository;

    @DisplayName("Get all authors in library")
    @Test
    void getAllAuthors() {
        List<Author> actualAuthors = authorRepository.findAll();
        assertThat(actualAuthors).hasSize(AUTHORS_COUNT);
    }

    @DisplayName("Get author by id")
    @Test
    void getAuthorByIdTest() {
        Optional<Author> actualAuthor = authorRepository.findById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("firstName", EXISTING_AUTHOR_FIRSTNAME)
                .hasFieldOrPropertyWithValue("lastName", EXISTING_AUTHOR_LASTNAME);
    }
}