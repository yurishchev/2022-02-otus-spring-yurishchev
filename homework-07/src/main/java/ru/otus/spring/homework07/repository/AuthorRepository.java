package ru.otus.spring.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework07.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
