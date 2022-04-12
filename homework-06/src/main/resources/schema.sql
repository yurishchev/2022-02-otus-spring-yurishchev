DROP TABLE IF EXISTS books_genres;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS books;

CREATE TABLE genres
(
    id   BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE authors
(
    id        BIGINT IDENTITY PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname  VARCHAR(255) NOT NULL
);

CREATE TABLE books
(
    id        BIGINT IDENTITY PRIMARY KEY,
    author_id BIGINT NOT NULL,
    title     VARCHAR(255),

    FOREIGN KEY (author_id) REFERENCES authors (id)
);

CREATE TABLE books_genres
(
    book_id  BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,

    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

CREATE TABLE comments
(
    id      BIGINT IDENTITY PRIMARY KEY,
    book_id BIGINT NOT NULL,
    text    VARCHAR(255) NOT NULL,
    author  VARCHAR(255),

    FOREIGN KEY (book_id) REFERENCES books (id)
);
