package ru.otus.spring.homework05.domain;

public class Book {
    private final int id;
    private final String title;
    private final Author author;
    private final Genre genre;

    public Book(int id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }
}
