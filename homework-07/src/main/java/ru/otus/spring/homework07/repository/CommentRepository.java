package ru.otus.spring.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework07.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);
}
