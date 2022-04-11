package ru.otus.spring.homework06.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework06.domain.Author;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.domain.Genre;
import ru.otus.spring.homework06.exception.AppException;
import ru.otus.spring.homework06.repository.AuthorRepository;
import ru.otus.spring.homework06.repository.BookRepository;
import ru.otus.spring.homework06.repository.GenreRepository;
import ru.otus.spring.homework06.service.LibraryService;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class LibraryServiceImpl implements LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public LibraryServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findBookById(id);
        if (book.isEmpty()) {
            throw new AppException("Couldn't find book with id=" + id);
        }
        return book.get();
    }

    @Override
    public Book createBook(String title, Long authorId, Long genreId) {
        Book book = createBookModel(null, title, authorId, genreId);
        return bookRepository.saveBook(book);
    }

    @Override
    public void updateBook(Long id, String title, Long authorId, Long genreId) {
        Book book = createBookModel(id, title, authorId, genreId);
        try {
            bookRepository.saveBook(book);
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new AppException("Couldn't update book with id=" + id, e);
        }
    }

    private Book createBookModel(Long id, String title, Long authorId, Long genreId) {
        Optional<Author> author = authorRepository.findAuthorById(authorId);
        if (author.isEmpty()) {
            throw new AppException("Can't find author with id=" + authorId);
        }
        Optional<Genre> genre = genreRepository.findGenreById(genreId);
        if (genre.isEmpty()) {
            throw new AppException("Can't find genre with id=" + genreId);
        }
        return new Book(id, title, author.get(), Collections.singletonList(genre.get()));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.deleteBook(id)) {
            throw new AppException("Couldn't find book with id=" + id);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}
