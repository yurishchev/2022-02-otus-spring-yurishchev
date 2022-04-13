package ru.otus.spring.homework06.repository.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.homework06.domain.Author;
import ru.otus.spring.homework06.repository.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findById(Long id) {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.id = :id", Author.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
