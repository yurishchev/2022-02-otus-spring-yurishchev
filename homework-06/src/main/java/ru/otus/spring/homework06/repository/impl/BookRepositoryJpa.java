package ru.otus.spring.homework06.repository.impl;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.repository.BookRepository;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-with-all-relations");
        TypedQuery<Book> query = em.createQuery("SELECT DISTINCT b FROM Book b", Book.class);
        query.setHint(QueryHints.HINT_FETCHGRAPH, entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-with-all-relations");
        return Optional.ofNullable(em.find(Book.class, id,
                Collections.singletonMap("javax.persistence.fetchgraph", entityGraph)));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            book = em.merge(book);
        }

        return book;
    }

    @Override
    public void delete(Book book) {
        // in order to delete all dependent entities using JPA I prefer to use remove over custom delete JPQL
        em.remove(book);
    }
}
