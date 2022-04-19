package ru.otus.spring.homework07.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework07.domain.Book;
import ru.otus.spring.homework07.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph("book-with-all-relations")
    Optional<Book> findById(Long id);

    @EntityGraph("book-with-all-relations")
    List<Book> findAll();
}
