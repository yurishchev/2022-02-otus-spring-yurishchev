package ru.otus.spring.homework05.domain;

public class Book {
    private Long id;
    private final String title;
    private final Long authorId;
    private final Long genreId;

    private final Author author;
    private final Genre genre;


    public Book(Long id, String title, Long authorId, Long genreId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
        this.author = null;
        this.genre = null;
    }

    public Book(Long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.authorId = author.getId();
        this.genre = genre;
        this.genreId = genre.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }
}
