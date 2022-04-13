package ru.otus.spring.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework06.domain.Author;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.domain.Genre;
import ru.otus.spring.homework06.repository.impl.BookRepositoryJpa;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Book Dao Test Suite")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {
    private static final int BOOKS_COUNT = 6;

    private static final long EXISTING_BOOK_ID = 1;
    private static final String UPDATED_BOOK_TITLE = "Peace and War";

    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_FIRSTNAME = "Lev";
    private static final String EXISTING_AUTHOR_LASTNAME = "Tolstoy";
    private static final long ANOTHER_EXISTING_AUTHOR_ID = 1;

    private static final long EXISTING_GENRE_ID = 1;


    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepositoryJpa bookRepository;


    @DisplayName("Get all books in library")
    @Test
    void getAllBooks() {
        List<Book> actualBooks = bookRepository.findAll();
        assertThat(actualBooks).hasSize(BOOKS_COUNT);
    }

    @DisplayName("Get book by id")
    @Test
    void getBookByIdTest() {
        Book expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        Optional<Book> actualBookOptional = bookRepository.findById(EXISTING_BOOK_ID);

        assertThat(actualBookOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Create book")
    @Test
    void createBookTest() {
        Author expectedAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        Genre expectedGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        Set<Genre> expectedGenres = Collections.singleton(expectedGenre);
        Book newBook = new Book(null, UPDATED_BOOK_TITLE, expectedAuthor, expectedGenres);
        newBook = bookRepository.save(newBook);
        em.flush();

        Long newBookId = newBook.getId();
        assertThat(newBookId).isNotNull();

        Optional<Book> actualBookOptional = bookRepository.findById(newBookId);
        assertThat(actualBookOptional).isPresent();

        Book actualBook = actualBookOptional.get();
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getTitle()).isEqualTo(UPDATED_BOOK_TITLE);
        assertThat(actualBook.getAuthor())
                .hasFieldOrPropertyWithValue("id", EXISTING_AUTHOR_ID)
                .hasFieldOrPropertyWithValue("firstName", EXISTING_AUTHOR_FIRSTNAME)
                .hasFieldOrPropertyWithValue("lastName", EXISTING_AUTHOR_LASTNAME);
        assertThat(actualBook).hasFieldOrPropertyWithValue("genres", expectedGenres);


    }

    @DisplayName("Update book")
    @Test
    void updateBookTest() {
        Author expectedAuthor = em.find(Author.class, ANOTHER_EXISTING_AUTHOR_ID);
        Book expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        expectedBook.setTitle(UPDATED_BOOK_TITLE);
        expectedBook.setAuthor(expectedAuthor);

        bookRepository.save(expectedBook);
        em.flush();

        Book actualBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(actualBook.getTitle()).isEqualTo(UPDATED_BOOK_TITLE);
        assertThat(actualBook.getAuthor()).isEqualTo(expectedAuthor);
    }

    @DisplayName("Delete book")
    @Test
    void deleteBookTest() {
        Book actualBook = em.find(Book.class, EXISTING_BOOK_ID);
        bookRepository.delete(actualBook);
        em.flush();

        actualBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(actualBook).isNull();
    }
}