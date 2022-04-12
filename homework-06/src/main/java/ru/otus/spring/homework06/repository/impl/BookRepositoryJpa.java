package ru.otus.spring.homework06.repository.impl;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
        EntityGraph<?> entityGraph = em.getEntityGraph("book-with-author-and-genres");
        TypedQuery<Book> query = em.createQuery("SELECT DISTINCT b FROM Book b", Book.class);
        query.setHint(QueryHints.HINT_FETCHGRAPH, entityGraph);
        List<Book> books = query.getResultList();

/*
        String jpql = "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.comments LEFT JOIN b.author WHERE b IN :books";
        books = em.createQuery(jpql, Book.class)
                .setParameter("books", books)
                //.setHint(QueryHints.HINT_FETCHGRAPH, entityGraph)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
*/
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-with-author-and-genres");
        return Optional.ofNullable(em.find(Book.class, id,
                Collections.singletonMap("javax.persistence.fetchgraph", entityGraph)));
        //return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    @Transactional
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            book = em.merge(book);
        }

        return book;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() == 1;
    }
}
