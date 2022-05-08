package ru.otus.spring.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.homework07.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    //@EntityGraph(value = "book-with-author") // Note - this performs a cartesian product over genres table!
    @Query("select b from Book b join fetch b.author where b.id = :id")
    Optional<Book> findById(@Param("id") Long id);

    @Query("select b from Book b join fetch b.author")
    List<Book> findAll();

}
