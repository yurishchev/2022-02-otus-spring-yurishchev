package ru.otus.spring.homework05.library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework05.dao.AuthorDao;
import ru.otus.spring.homework05.dao.BookDao;
import ru.otus.spring.homework05.dao.GenreDao;
import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;
import ru.otus.spring.homework05.exception.AppException;
import ru.otus.spring.homework05.service.LibraryService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@DisplayName("Library Service Tests")
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class LibraryServiceTest {
    private static final int EXPECTED_AUTHORS_COUNT = 6;
    private static final int EXPECTED_GENRES_COUNT = 3;
    private static final int EXPECTED_BOOKS_COUNT = 6;

    private static final long EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_TITLE = "War and Peace";
    private static final long NEW_BOOK_ID = 7;
    private static final String UPDATED_BOOK_TITLE = "Peace and War";

    private static final long EXISTING_AUTHOR_ID = 1;
    private static final long NON_EXISTING_AUTHOR_ID = 100;
    private static final String EXISTING_AUTHOR_FIRSTNAME = "Lev";
    private static final String EXISTING_AUTHOR_LASTNAME = "Tolstoy";
    private static final long ANOTHER_EXISTING_AUTHOR_ID = 1;
    private static final String ANOTHER_EXISTING_AUTHOR_FIRSTNAME = "Lev";
    private static final String ANOTHER_EXISTING_AUTHOR_LASTNAME = "Tolstoy";

    private static final long EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Fiction";
    private static final long ANOTHER_EXISTING_GENRE_ID = 2;
    private static final String ANOTHER_EXISTING_GENRE_NAME = "Fantasy";

    @MockBean
    private BookDao bookDao;
    @MockBean
    private AuthorDao authorDao;
    @MockBean
    private GenreDao genreDao;

    @Autowired
    private LibraryService service;


    @DisplayName("Check available books")
    @Test
    void shouldContainProperSizeOfBooks() {
        List<Book> books = mock(List.class);
        when(books.size()).thenReturn(EXPECTED_BOOKS_COUNT);
        when(bookDao.getAllBooks()).thenReturn(books);

        assertThat(service.getAllBooks().size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Get book by id")
    @Test
    void getBookByIdTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, expectedAuthor, expectedGenre);
        when(bookDao.getBookById(expectedBook.getId())).thenReturn(expectedBook);

        Book actualBook = service.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Create book")
    @Test
    void createBookTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        when(authorDao.getAuthorById(EXISTING_AUTHOR_ID)).thenReturn(expectedAuthor);
        when(genreDao.getGenreById(EXISTING_GENRE_ID)).thenReturn(expectedGenre);
        when(bookDao.createBook(any(Book.class))).thenReturn(NEW_BOOK_ID);

        Book actualBook = service.createBook(UPDATED_BOOK_TITLE, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);
        assertThat(actualBook.getId()).isEqualTo(NEW_BOOK_ID);
    }

    @DisplayName("Delete book")
    @Test
    void deleteBookTest() {
        when(bookDao.deleteBook(EXISTING_BOOK_ID)).thenReturn(true);
        assertThatCode(() -> service.deleteBook(EXISTING_BOOK_ID)).doesNotThrowAnyException();

        when(bookDao.deleteBook(EXISTING_BOOK_ID)).thenReturn(false);
        assertThatThrownBy(() -> service.deleteBook(EXISTING_BOOK_ID)).isInstanceOf(AppException.class);
    }

    @DisplayName("Update book")
    @Test
    void updateBookTest() {
        Author expectedAuthor = new Author(ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_AUTHOR_FIRSTNAME, ANOTHER_EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(ANOTHER_EXISTING_GENRE_ID, ANOTHER_EXISTING_GENRE_NAME);
        when(authorDao.getAuthorById(ANOTHER_EXISTING_AUTHOR_ID)).thenReturn(expectedAuthor);
        when(genreDao.getGenreById(ANOTHER_EXISTING_GENRE_ID)).thenReturn(expectedGenre);
        when(bookDao.updateBook(any(Book.class))).thenReturn(true);

        assertThatCode(() -> service.updateBook(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_GENRE_ID)).doesNotThrowAnyException();
    }

    @DisplayName("Update book with missing author")
    @Test
    void updateBookWithMissingAuthor() {
        Genre expectedGenre = new Genre(ANOTHER_EXISTING_GENRE_ID, ANOTHER_EXISTING_GENRE_NAME);
        when(authorDao.getAuthorById(NON_EXISTING_AUTHOR_ID)).thenReturn(null);
        when(genreDao.getGenreById(EXISTING_GENRE_ID)).thenReturn(expectedGenre);

        assertThatThrownBy(() -> service.updateBook(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, NON_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_GENRE_ID)).isInstanceOf(AppException.class);
    }

    @DisplayName("Update book with missing genre")
    @Test
    void updateBookWithMissingGenre() {
        Author expectedAuthor = new Author(ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_AUTHOR_FIRSTNAME, ANOTHER_EXISTING_AUTHOR_LASTNAME);
        when(authorDao.getAuthorById(NON_EXISTING_AUTHOR_ID)).thenReturn(expectedAuthor);
        when(genreDao.getGenreById(ANOTHER_EXISTING_GENRE_ID)).thenReturn(null);

        assertThatThrownBy(() -> service.updateBook(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_GENRE_ID)).isInstanceOf(AppException.class);
    }

    @DisplayName("Check available authors")
    @Test
    void shouldContainProperSizeOfAuthors() {
        List<Author> authors = mock(List.class);
        when(authors.size()).thenReturn(EXPECTED_AUTHORS_COUNT);
        when(authorDao.getAllAuthors()).thenReturn(authors);

        assertThat(service.getAllAuthors().size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("Check available genres")
    @Test
    void shouldContainProperSizeOfGenres() {
        List<Genre> genres = mock(List.class);
        when(genres.size()).thenReturn(EXPECTED_GENRES_COUNT);
        when(genreDao.getAllGenres()).thenReturn(genres);

        assertThat(service.getAllGenres().size()).isEqualTo(EXPECTED_GENRES_COUNT);
    }
}
