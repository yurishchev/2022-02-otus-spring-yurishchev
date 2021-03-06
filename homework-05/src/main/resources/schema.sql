DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS GENRES;

CREATE TABLE GENRES
(
    ID   BIGINT IDENTITY PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);

CREATE TABLE AUTHORS
(
    ID   BIGINT IDENTITY PRIMARY KEY,
    FIRSTNAME VARCHAR(255) NOT NULL,
    LASTNAME VARCHAR(255) NOT NULL
);

CREATE TABLE BOOKS
(
    ID        BIGINT IDENTITY PRIMARY KEY,
    TITLE     VARCHAR(255),
    AUTHOR_ID BIGINT,
    GENRE_ID  BIGINT,

    FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS (ID),
    FOREIGN KEY (GENRE_ID) REFERENCES GENRES (ID)
);
