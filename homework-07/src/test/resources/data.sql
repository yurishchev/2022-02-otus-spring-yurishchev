INSERT INTO genres (id, name) VALUES (1, 'Fiction');
INSERT INTO genres (id, name) VALUES (2, 'Fantasy');
INSERT INTO genres (id, name) VALUES (3, 'Children literature');

INSERT INTO authors (id, firstname, lastname) VALUES (1, 'Lev', 'Tolstoy');
INSERT INTO authors (id, firstname, lastname) VALUES (2, 'Fedor', 'Dostoevsky');
INSERT INTO authors (id, firstname, lastname) VALUES (3, 'John', 'Tolkien');
INSERT INTO authors (id, firstname, lastname) VALUES (4, 'Joanne', 'Rawlings');
INSERT INTO authors (id, firstname, lastname) VALUES (5, 'Grimm', 'Brothers');
INSERT INTO authors (id, firstname, lastname) VALUES (6, 'Nikolay', 'Nosov');

INSERT INTO books (id, author_id, title) VALUES (1, 1, 'War and Peace');
INSERT INTO books (id, author_id, title) VALUES (2, 1, 'Anna Karenina');
INSERT INTO books (id, author_id, title) VALUES (3, 2, 'Crime and Punishment');
INSERT INTO books (id, author_id, title) VALUES (4, 3, 'The Lord of the Rings');
INSERT INTO books (id, author_id, title) VALUES (5, 5, 'Harry Potter');
INSERT INTO books (id, author_id, title) VALUES (6, 6, 'Nesnaika');

INSERT INTO books_genres (book_id, genre_id) VALUES (1, 1);
INSERT INTO books_genres (book_id, genre_id) VALUES (1, 3);
INSERT INTO books_genres (book_id, genre_id) VALUES (2, 1);
INSERT INTO books_genres (book_id, genre_id) VALUES (3, 1);
INSERT INTO books_genres (book_id, genre_id) VALUES (4, 2);
INSERT INTO books_genres (book_id, genre_id) VALUES (5, 2);
INSERT INTO books_genres (book_id, genre_id) VALUES (5, 3);
INSERT INTO books_genres (book_id, genre_id) VALUES (6, 3);

INSERT INTO comments (id, book_id, text, author) VALUES (1, 1, 'Outstanding masterpiece!', 'Vasil Bykov');
INSERT INTO comments (id, book_id, text, author) VALUES (2, 1, 'Nice, but can be better', null);
INSERT INTO comments (id, book_id, text, author) VALUES (3, 4, 'Читается на одном дыхании!', 'Владимир');
