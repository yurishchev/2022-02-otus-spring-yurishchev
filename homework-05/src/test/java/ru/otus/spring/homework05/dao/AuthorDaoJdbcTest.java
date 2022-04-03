package ru.otus.spring.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework05.dao.impl.AuthorDaoJdbc;
import ru.otus.spring.homework05.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Author Dao Test Suite")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_FIRSTNAME = "Lev";
    private static final String EXISTING_AUTHOR_LASTNAME = "Tolstoy";

    @Autowired
    private AuthorDaoJdbc authorDao;

    @DisplayName("Get all authors in library")
    @Test
    void getAllAuthors() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        List<Author> actualAuthors = authorDao.getAllAuthors();
        assertThat(actualAuthors)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAnyOf(expectedAuthor);
    }

    @DisplayName("Get author by id")
    @Test
    void getAuthorByIdTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        Author actualAuthor = authorDao.getAuthorById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}