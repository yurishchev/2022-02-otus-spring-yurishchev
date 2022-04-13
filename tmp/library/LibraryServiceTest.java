package ru.otus.spring.homework06.library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework06.domain.Author;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.domain.Genre;
import ru.otus.spring.homework06.exception.AppException;
import ru.otus.spring.homework06.repository.AuthorRepository;
import ru.otus.spring.homework06.repository.BookRepository;
import ru.otus.spring.homework06.repository.GenreRepository;
import ru.otus.spring.homework06.service.LibraryService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private LibraryService service;

    @DisplayName("Check available books")
    @Test
    void shouldContainProperSizeOfBooks() {
        List<Book> books = mock(List.class);
        when(books.size()).thenReturn(EXPECTED_BOOKS_COUNT);
        when(bookRepository.findAll()).thenReturn(books);

        assertThat(service.getAllBooks().size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Get book by id")
    @Test
    void getBookByIdTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, expectedAuthor, Collections.singleton(expectedGenre));
        when(bookRepository.findById(expectedBook.getId()).get()).thenReturn(expectedBook);

        Book actualBook = service.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Create book")
    @Test
    void createBookTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRSTNAME, EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        when(authorRepository.findById(EXISTING_AUTHOR_ID).get()).thenReturn(expectedAuthor);
        when(genreRepository.findById(EXISTING_GENRE_ID).get()).thenReturn(expectedGenre);
        //when(bookRepository.save(any(Book.class))).thenReturn(NEW_BOOK_ID);

        Book actualBook = service.createBook(UPDATED_BOOK_TITLE, EXISTING_AUTHOR_ID, Collections.singletonList(EXISTING_GENRE_ID));
        assertThat(actualBook.getId()).isEqualTo(NEW_BOOK_ID);
    }

    @DisplayName("Delete book")
    @Test
    void deleteBookTest() {
        assertThatCode(() -> service.deleteBook(EXISTING_BOOK_ID)).doesNotThrowAnyException();

        assertThatThrownBy(() -> service.deleteBook(EXISTING_BOOK_ID)).isInstanceOf(AppException.class);
    }

    @DisplayName("Update book")
    @Test
    void updateBookTest() {
        Author expectedAuthor = new Author(ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_AUTHOR_FIRSTNAME, ANOTHER_EXISTING_AUTHOR_LASTNAME);
        Genre expectedGenre = new Genre(ANOTHER_EXISTING_GENRE_ID, ANOTHER_EXISTING_GENRE_NAME);
        when(authorRepository.findById(ANOTHER_EXISTING_AUTHOR_ID).get()).thenReturn(expectedAuthor);
        when(genreRepository.findById(ANOTHER_EXISTING_GENRE_ID).get()).thenReturn(expectedGenre);
        //when(bookRepository.update(any(Book.class))).thenReturn(true);

        assertThatCode(() -> service.updateBook(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, ANOTHER_EXISTING_AUTHOR_ID, Collections.singletonList(ANOTHER_EXISTING_GENRE_ID))).doesNotThrowAnyException();
    }

    @DisplayName("Update book with missing author")
    @Test
    void updateBookWithMissingAuthor() {
        Genre expectedGenre = new Genre(ANOTHER_EXISTING_GENRE_ID, ANOTHER_EXISTING_GENRE_NAME);
        when(authorRepository.findById(NON_EXISTING_AUTHOR_ID)).thenReturn(null);
        when(genreRepository.findById(EXISTING_GENRE_ID).get()).thenReturn(expectedGenre);

        assertThatThrownBy(() -> service.updateBook(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, NON_EXISTING_AUTHOR_ID, Collections.singletonList(ANOTHER_EXISTING_GENRE_ID))).isInstanceOf(AppException.class);
    }

    @DisplayName("Update book with missing genre")
    @Test
    void updateBookWithMissingGenre() {
        Author expectedAuthor = new Author(ANOTHER_EXISTING_AUTHOR_ID, ANOTHER_EXISTING_AUTHOR_FIRSTNAME, ANOTHER_EXISTING_AUTHOR_LASTNAME);
        when(authorRepository.findById(NON_EXISTING_AUTHOR_ID).get()).thenReturn(expectedAuthor);
        when(genreRepository.findById(ANOTHER_EXISTING_GENRE_ID)).thenReturn(null);

        assertThatThrownBy(() -> service.updateBook(EXISTING_BOOK_ID, UPDATED_BOOK_TITLE, ANOTHER_EXISTING_AUTHOR_ID, Collections.singletonList(ANOTHER_EXISTING_GENRE_ID))).isInstanceOf(AppException.class);
    }

    @DisplayName("Check available authors")
    @Test
    void shouldContainProperSizeOfAuthors() {
        List<Author> authors = mock(List.class);
        when(authors.size()).thenReturn(EXPECTED_AUTHORS_COUNT);
        when(authorRepository.findAll()).thenReturn(authors);

        assertThat(service.getAllAuthors().size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("Check available genres")
    @Test
    void shouldContainProperSizeOfGenres() {
        List<Genre> genres = mock(List.class);
        when(genres.size()).thenReturn(EXPECTED_GENRES_COUNT);
        when(genreRepository.findAll()).thenReturn(genres);

        assertThat(service.getAllGenres().size()).isEqualTo(EXPECTED_GENRES_COUNT);
    }

}