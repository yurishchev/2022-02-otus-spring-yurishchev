package ru.otus.spring.homework05.domain;

public class Genre {
    private final Long id;
    private final String name;

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
