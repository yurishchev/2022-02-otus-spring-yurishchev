package ru.otus.spring.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;
import ru.otus.spring.homework05.exception.AppException;
import ru.otus.spring.homework05.service.LibraryService;

import static org.assertj.core.api.Assertions.*;


@DisplayName("Library Service Integration Tests")
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class LibraryServiceTest {
    private static final long EXPECTED_AUTHORS_COUNT = 6;
    private static final long EXPECTED_GENRES_COUNT = 3;
    private static final long EXPECTED_BOOKS_COUNT = 6;

    private static final long EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_TITLE = "War and Peace";
    private static final long NEW_BOOK_ID = 7;
    private static final String UPDATED_BOOK_TITLE = "Peace and War";

    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_FIRSTNAME = "Lev";
    private static final String EXISTING_AUTHOR_LASTNAME = "Tolstoy";
    private static final long ANOTHER_EXISTING_AUTHOR_ID = 1;
    private static final String ANOTHER_EXISTING_AUTHOR_FIRSTNAME = "Lev";
    private static final String ANOTHER_EXISTING_AUTHOR_LASTNAME = "Tolstoy";

    private static final long EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Fiction";
    private static final long ANOTHER_EXISTING_GENRE_ID = 2;
    private static final String ANOTHER_EXISTING_GENRE_NAME = "Fantasy";

    @Autowired
    private LibraryService service;


    @DisplayName("Check available authors and genres")
    @Test
    void shouldContainAuthorsAndGenres() {
        assertThat(service.getAllGenres().size()).isEqualTo(EXPECTED_GENRES_COUNT);

        assertThat(service.getAllAuthors().size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("Get book by id")
    @Test
    void getBookByIdTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, expectedAuthor, expectedGenre);
        Book actualBook = service.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Create and Delete book")
    @Test
    void createAndDeleteBookTest() {
        Book actualBook = service.createBook("Voskresenie", EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);
        assertThat(actualBook.getId()).isEqualTo(NEW_BOOK_ID);
        assertThat(service.getAllBooks().size()).isEqualTo(EXPECTED_BOOKS_COUNT + 1);

        assertThatCode(() -> service.getBookById(NEW_BOOK_ID)).doesNotThrowAnyException();
        assertThatCode(() -> service.deleteBook(NEW_BOOK_ID)).doesNotThrowAnyException();
        assertThatThrownBy(() -> service.getBookById(NEW_BOOK_ID)).isInstanceOf(AppException.class);

        assertThat(service.getAllBooks().size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Update book")
    @Test
    void updateBookByIdTest() {
        Author expectedAuthor = new Author(ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_AUTHOR_FIRSTNAME, ANOTHER_EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(ANOTHER_EXISTING_GENRE_ID, ANOTHER_EXISTING_GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, expectedAuthor, expectedGenre);

        assertThatCode(() -> service.updateBook(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_GENRE_ID)).doesNotThrowAnyException();

        Book actualBook = service.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

        assertThat(service.getAllBooks().size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }
}
