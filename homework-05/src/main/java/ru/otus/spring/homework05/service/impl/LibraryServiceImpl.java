package ru.otus.spring.homework05.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework05.dao.LibraryDao;
import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;
import ru.otus.spring.homework05.exception.AppException;
import ru.otus.spring.homework05.service.LibraryService;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final LibraryDao dao;

    public LibraryServiceImpl(LibraryDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Book> getAllBooks() {
        return dao.getAllBooks();
    }

    @Override
    public Book getBookById(Long id) {
        try {
            return dao.getBookById(id);
        } catch (EmptyResultDataAccessException ere) {
            throw new AppException("Couldn't find book with id=" + id);
        }
    }

    @Override
    public Book createBook(String title, Long authorId, Long genreId) {
        Book book = createBookModel(null, title, authorId, genreId);

        long id = dao.createBook(book);
        book.setId(id);
        return book;
    }

    @Override
    public void updateBook(Long id, String title, Long authorId, Long genreId) {
        Book book = createBookModel(id, title, authorId, genreId);
        if (!dao.updateBook(book)) {
            throw new AppException("Couldn't find book with id=" + id);
        }
    }

    private Book createBookModel(Long id, String title, Long authorId, Long genreId) {
        Author author = dao.getAuthorById(authorId);
        Genre genre = dao.getGenreById(genreId);
        if (author == null) {
            throw new AppException("Can't find author with id=" + authorId);
        }
        if (genre == null) {
            throw new AppException("Can't find genre with id=" + genreId);
        }
        return new Book(id, title, authorId, genreId);
    }

    @Override
    public void deleteBook(Long id) {
        if (!dao.deleteBook(id)) {
            throw new AppException("Couldn't find book with id=" + id);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return dao.getAllAuthors();
    }

    @Override
    public List<Genre> getAllGenres() {
        return dao.getAllGenres();
    }
}
