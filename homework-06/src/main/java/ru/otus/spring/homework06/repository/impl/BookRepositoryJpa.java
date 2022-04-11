package ru.otus.spring.homework06.repository.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
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
        // TODO fetch necessary associated data
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        // TODO fetch necessary associated data
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.id = :id", Book.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Book saveBook(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            book = em.merge(book);
        }

        return book;
    }

    @Override
    public boolean deleteBook(Long id) {
        TypedQuery<Book> query = em.createQuery("delete from Book b where b.id = :id", Book.class);
        query.setParameter("id", id);
        return query.executeUpdate() == 1;
    }
}
