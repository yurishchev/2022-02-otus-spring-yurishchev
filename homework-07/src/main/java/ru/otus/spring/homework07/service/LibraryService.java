package ru.otus.spring.homework07.service;

import ru.otus.spring.homework07.domain.Author;
import ru.otus.spring.homework07.domain.Book;
import ru.otus.spring.homework07.domain.Comment;
import ru.otus.spring.homework07.domain.Genre;

import java.util.List;

public interface LibraryService {
    List<Author> getAllAuthors();

    List<Genre> getAllGenres();

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book createBook(String title, Long authorId, List<Long> genreIds);

    void updateBook(Long id, String title, Long authorId, List<Long> genreIds);

    void deleteBook(Long id);

    Comment getCommentById(Long id);

    List<Comment> getAllCommentsByBookId(Long bookId);

    Comment createComment(Long bookId, String text, String from);

    void updateComment(Long id, String text, String from);

    void deleteComment(Long id);
}
