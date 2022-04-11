package ru.otus.spring.homework06.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENRES")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "BOOKS_GENRES",
            joinColumns = @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"))
    private List<Genre> genres;
}
