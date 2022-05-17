package ru.otus.spring.homework07.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.homework07.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(value = "book-with-author", type = EntityGraph.EntityGraphType.LOAD)
    //@Query("select b from Book b join fetch b.author where b.id = :id")
    Optional<Book> findById(@Param("id") Long id);

    @EntityGraph(value = "book-with-author", type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findAll();

}
