package ru.otus.spring.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.homework07.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
