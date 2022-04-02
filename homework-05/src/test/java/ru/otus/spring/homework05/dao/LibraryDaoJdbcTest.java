package ru.otus.spring.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.homework05.dao.impl.LibraryDaoJdbc;
import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Library Dao Test Suite")
@JdbcTest
@Import(LibraryDaoJdbc.class)
class LibraryDaoJdbcTest {

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
    private LibraryDaoJdbc libraryDao;


    @DisplayName("Create book")
    @Test
    void createBookTest() {
        Book expectedBook = new Book(null, "Voskresenie", EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);
        long actualBookId = libraryDao.createBook(expectedBook);

        assertThat(actualBookId).isEqualTo(NEW_BOOK_ID);
    }

    @DisplayName("Get book by id")
    @Test
    void getBookByIdTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, expectedAuthor, expectedGenre);
        Book actualBook = libraryDao.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Update book by id")
    @Test
    void updateBookByIdTest() {
        Author expectedAuthor = new Author(ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_AUTHOR_FIRSTNAME, ANOTHER_EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(ANOTHER_EXISTING_GENRE_ID, ANOTHER_EXISTING_GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, expectedAuthor, expectedGenre);
        boolean result = libraryDao.updateBook(expectedBook);
        Book actualBook = libraryDao.getBookById(expectedBook.getId());

        assertThat(result).isTrue();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Delete book by id")
    @Test
    void deleteBookTest() {
        assertThatCode(() -> libraryDao.getBookById(EXISTING_BOOK_ID)).doesNotThrowAnyException();

        boolean result = libraryDao.deleteBook(EXISTING_BOOK_ID);

        assertThat(result).isTrue();
        assertThatThrownBy(() -> libraryDao.getBookById(EXISTING_BOOK_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Get all books in library")
    @Test
    void getAllBooks() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);
        List<Book> actualPersonList = libraryDao.getAllBooks();
        assertThat(actualPersonList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAnyOf(expectedBook);
    }

    @DisplayName("Get all authors in library")
    @Test
    void getAllAuthors() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        List<Author> actualAuthors = libraryDao.getAllAuthors();
        assertThat(actualAuthors)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAnyOf(expectedAuthor);
    }

}