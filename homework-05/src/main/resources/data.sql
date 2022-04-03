INSERT INTO GENRES (ID, NAME) VALUES (1, 'Fiction');
INSERT INTO GENRES (ID, NAME) VALUES (2, 'Fantasy');
INSERT INTO GENRES (ID, NAME) VALUES (3, 'Historical novel');
INSERT INTO GENRES (ID, NAME) VALUES (4, 'Children literature');

INSERT INTO AUTHORS (ID, FIRSTNAME, LASTNAME) VALUES (1, 'Lev', 'Tolstoy');
INSERT INTO AUTHORS (ID, FIRSTNAME, LASTNAME) VALUES (2, 'Fedor', 'Dostoevsky');
INSERT INTO AUTHORS (ID, FIRSTNAME, LASTNAME) VALUES (3, 'John', 'Tolkien');
INSERT INTO AUTHORS (ID, FIRSTNAME, LASTNAME) VALUES (4, 'Valentin', 'Pikul');
INSERT INTO AUTHORS (ID, FIRSTNAME, LASTNAME) VALUES (5, 'Joanne', 'Rawlings');
INSERT INTO AUTHORS (ID, FIRSTNAME, LASTNAME) VALUES (6, 'Grimm', 'Brothers');
INSERT INTO AUTHORS (ID, FIRSTNAME, LASTNAME) VALUES (7, 'Nikolay', 'Nosov');

INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (1, 'War and Peace', 1, 1);
INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (2, 'Anna Karenina', 1, 1);
INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (3, 'Idiot', 2, 1);
INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (4, 'Crime and Punishment', 2, 1);
INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (5, 'The Lord of the Rings', 3, 2);
INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (6, 'Harry Potter', 5, 4);
INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (7, 'Bayzaet', 4, 3);
INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (8, 'Nesnaika', 7, 4);
INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (9, 'Mishkina Kasha', 7, 4);
INSERT INTO BOOKS (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (10, 'Bremen Musicians', 6, 4);
