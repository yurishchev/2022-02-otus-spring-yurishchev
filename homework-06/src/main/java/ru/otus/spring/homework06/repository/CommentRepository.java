package ru.otus.spring.homework06.repository;

import ru.otus.spring.homework06.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> findById(Long id);

    List<Comment> findAllInBook(Long bookId);

    Comment save(Comment comment);

    boolean deleteById(Long id);
}
