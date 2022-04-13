package ru.otus.spring.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework06.domain.Author;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.domain.Comment;
import ru.otus.spring.homework06.domain.Genre;
import ru.otus.spring.homework06.repository.impl.BookRepositoryJpa;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Book Dao Test Suite")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

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
    private TestEntityManager em;

    @Autowired
    private BookRepositoryJpa bookRepository;


    @DisplayName("Create book")
    @Test
    void createBookTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        Set<Genre> expectedGenres = Collections.singleton(new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME));
        Book expectedBook = new Book(null, UPDATED_BOOK_TITLE, expectedAuthor, expectedGenres);
        Book actualBook = bookRepository.save(expectedBook);
        Long newBookId = actualBook.getId();

        assertThat(newBookId).isNotNull();
        assertThat(actualBook)
                .hasFieldOrPropertyWithValue("title", EXISTING_BOOK_TITLE)
                .hasFieldOrPropertyWithValue("firstName", EXISTING_AUTHOR_FIRSTNAME)
                .hasFieldOrPropertyWithValue("lastName", EXISTING_AUTHOR_LASTNAME);
        assertThat(actualBook).hasFieldOrPropertyWithValue("genres", expectedGenres);

        em.clear();

        actualBook = em.find(Book.class, newBookId);
        assertThat(actualBook.getId()).isEqualTo(newBookId);
    }

    @DisplayName("Get book by id")
    @Test
    void getBookByIdTest() {
        Book expectedStudent = em.find(Book.class, EXISTING_BOOK_ID);
        Optional<Book> optionalActualStudent = bookRepository.findById(EXISTING_BOOK_ID);
        assertThat(optionalActualStudent).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedStudent);
    }

/*
    @DisplayName("Update book")
    @Test
    void updateBookTest() {
        Comment actualComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        actualComment.setText(NEW_COMMENT_TEXT);
        actualComment.setFrom(NEW_COMMENT_FROM);
        em.detach(actualComment);

        commentRepository.save(actualComment);

        actualComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(actualComment.getText()).isEqualTo(NEW_COMMENT_TEXT);
        assertThat(actualComment.getFrom()).isEqualTo(NEW_COMMENT_FROM);
    }
*/

    @DisplayName("Delete book")
    @Test
    void deleteBookTest() {
        Book expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        bookRepository.delete(expectedBook);

        em.clear();

        Book comment = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(comment).isNull();
    }

/*
    @DisplayName("Update book by id")
    @Test
    void updateBookByIdTest() {
        Author expectedAuthor = new Author(ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_AUTHOR_FIRSTNAME, ANOTHER_EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(ANOTHER_EXISTING_GENRE_ID, ANOTHER_EXISTING_GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, expectedAuthor, expectedGenre);
        boolean result = bookDao.updateBook(expectedBook);
        Book actualBook = bookDao.getBookById(expectedBook.getId());

        assertThat(result).isTrue();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Delete book by id")
    @Test
    void deleteBookTest() {
        assertThatCode(() -> bookDao.getBookById(EXISTING_BOOK_ID)).doesNotThrowAnyException();

        boolean result = bookDao.deleteBook(EXISTING_BOOK_ID);

        assertThat(result).isTrue();
        assertThatThrownBy(() -> bookDao.getBookById(EXISTING_BOOK_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Get all books in library")
    @Test
    void getAllBooks() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, expectedAuthor, expectedGenre);
        List<Book> actualPersonList = bookDao.getAllBooks();
        assertThat(actualPersonList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAnyOf(expectedBook);
    }
 */
}