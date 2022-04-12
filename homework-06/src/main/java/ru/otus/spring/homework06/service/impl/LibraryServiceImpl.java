package ru.otus.spring.homework06.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework06.domain.Author;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.domain.Comment;
import ru.otus.spring.homework06.domain.Genre;
import ru.otus.spring.homework06.exception.AppException;
import ru.otus.spring.homework06.repository.AuthorRepository;
import ru.otus.spring.homework06.repository.BookRepository;
import ru.otus.spring.homework06.repository.CommentRepository;
import ru.otus.spring.homework06.repository.GenreRepository;
import ru.otus.spring.homework06.service.LibraryService;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class LibraryServiceImpl implements LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public LibraryServiceImpl(BookRepository bookRepository,
                              AuthorRepository authorRepository,
                              GenreRepository genreRepository,
                              CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    ///-------------- Book ---------------

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new AppException("Couldn't find book with id=" + id);
        }
        return book.get();
    }

    @Override
    public Book createBook(String title, Long authorId, List<Long> genreIds) {
        Book book = createBookModel(null, title, authorId, genreIds);
        return bookRepository.save(book);
    }

    @Override
    public void updateBook(Long id, String title, Long authorId, List<Long> genreIds) {
        Book book = createBookModel(id, title, authorId, genreIds);
        try {
            bookRepository.save(book);
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new AppException("Couldn't update book with id=" + id, e);
        }
    }

    private Book createBookModel(Long id, String title, Long authorId, List<Long> genreIds) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            throw new AppException("Can't find author with id=" + authorId);
        }
        Set<Genre> genres = genreIds.stream().map(genreId -> {
            Optional<Genre> genre = genreRepository.findById(genreId);
            if (genre.isEmpty()) {
                throw new AppException("Can't find genre with id=" + genreId);
            }
            return genre.get();
        }).collect(Collectors.toSet());

        return new Book(id, title, author.get(), genres);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.deleteById(id)) {
            throw new AppException("Couldn't delete book with id=" + id);
        }
    }

    ///-------------- Comment ---------------

    @Override
    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new AppException("Couldn't find comment with id=" + id);
        }
        return comment.get();
    }

    @Override
    public List<Comment> getAllCommentsByBookId(Long bookId) {
        return commentRepository.findAllInBook(bookId);
    }

    @Override
    public Comment createComment(Long bookId, String text, String from) {
        Book book = getBookById(bookId);
        Comment comment = new Comment(null, from, text, book);
        return commentRepository.save(comment);
    }

    @Override
    public void updateComment(Long id, String text, String from) {
        Comment comment = getCommentById(id);
        comment.setText(text);
        comment.setFrom(from);
        try {
            commentRepository.save(comment);
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new AppException("Couldn't update comment with id=" + id, e);
        }
    }

    @Override
    public void deleteComment(Long id) {
/*
        Comment comment = getCommentById(id);
        Book book = getBookById(comment.getBook().getId());
        book.removeComment(comment);
        commentRepository.deleteById(id);
*/


        if (!commentRepository.deleteById(id)) {
            throw new AppException("Couldn't delete comment with id=" + id);
        }
    }
}
