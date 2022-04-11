package ru.otus.spring.homework05.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework05.dao.AuthorDao;
import ru.otus.spring.homework05.dao.BookDao;
import ru.otus.spring.homework05.dao.GenreDao;
import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;
import ru.otus.spring.homework05.exception.AppException;
import ru.otus.spring.homework05.service.LibraryService;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public LibraryServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public Book getBookById(Long id) {
        try {
            return bookDao.getBookById(id);
        } catch (EmptyResultDataAccessException ere) {
            throw new AppException("Couldn't find book with id=" + id);
        }
    }

    @Override
    public Book createBook(String title, Long authorId, Long genreId) {
        Book book = createBookModel(null, title, authorId, genreId);

        long id = bookDao.createBook(book);
        book.setId(id);
        return book;
    }

    @Override
    public void updateBook(Long id, String title, Long authorId, Long genreId) {
        Book book = createBookModel(id, title, authorId, genreId);
        if (!bookDao.updateBook(book)) {
            throw new AppException("Couldn't find book with id=" + id);
        }
    }

    private Book createBookModel(Long id, String title, Long authorId, Long genreId) {
        Author author = authorDao.getAuthorById(authorId);
        Genre genre = genreDao.getGenreById(genreId);
        if (author == null) {
            throw new AppException("Can't find author with id=" + authorId);
        }
        if (genre == null) {
            throw new AppException("Can't find genre with id=" + genreId);
        }
        return new Book(id, title, author, genre);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookDao.deleteBook(id)) {
            throw new AppException("Couldn't find book with id=" + id);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }
}
