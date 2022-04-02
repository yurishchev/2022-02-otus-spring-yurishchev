package ru.otus.spring.homework05.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework05.dao.LibraryDao;
import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class LibraryDaoJdbc implements LibraryDao {
    private final NamedParameterJdbcOperations jdbc;

    public LibraryDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public Book getBookById(Long id) {
        String sql = "SELECT b.ID, b.TITLE, b.AUTHOR_ID, b.GENRE_ID, " +
                "a.FIRSTNAME as AUTHOR_FIRSTNAME, a.LASTNAME as AUTHOR_LASTNAME, g.NAME as GENRE_NAME " +
                "FROM BOOKS b, AUTHORS a, GENRES g " +
                "WHERE b.AUTHOR_ID = a.ID AND b.GENRE_ID = g.ID AND b.ID=:id";
        return jdbc.queryForObject(sql, Collections.singletonMap("id", id), new BookFullInfoMapper());
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query("SELECT ID, TITLE, AUTHOR_ID, GENRE_ID FROM BOOKS", new BookMapper());
    }

    @Override
    public long createBook(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthorId());
        params.addValue("genreId", book.getGenreId());

        KeyHolder kh = new GeneratedKeyHolder();
        String sql = "INSERT INTO BOOKS (TITLE, AUTHOR_ID, GENRE_ID) VALUES (:title, :authorId, :genreId)";
        jdbc.update(sql, params, kh);

        return Objects.requireNonNull(kh.getKey()).longValue();
    }

    @Override
    public boolean updateBook(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthorId());
        params.addValue("genreId", book.getGenreId());

        String sql = "UPDATE BOOKS SET TITLE=:title, AUTHOR_ID=:authorId, GENRE_ID=:genreId WHERE ID=:id";
        return jdbc.update(sql, params) > 0;
    }

    @Override
    public boolean deleteBook(Long id) {
        String sql = "DELETE FROM BOOKS WHERE ID = :id";
        return jdbc.update(sql, new MapSqlParameterSource("id", id)) > 0;
    }

    @Override
    public Author getAuthorById(Long id) {
        String sql = "SELECT ID, FIRSTNAME, LASTNAME FROM AUTHORS WHERE ID=:id";
        return jdbc.queryForObject(sql, Collections.singletonMap("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbc.query("SELECT ID, FIRSTNAME, LASTNAME FROM AUTHORS", new AuthorMapper());
    }

    @Override
    public Genre getGenreById(Long id) {
        String sql = "SELECT ID, NAME FROM Genres WHERE ID=:id";
        return jdbc.queryForObject(sql, Collections.singletonMap("id", id), new GenreMapper());
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbc.query("SELECT ID, NAME FROM GENRES", new GenreMapper());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String title = resultSet.getString("TITLE");
            long authorId = resultSet.getLong("AUTHOR_ID");
            long genreId = resultSet.getLong("GENRE_ID");
            return new Book(id, title, authorId, genreId);
        }
    }

    private static class BookFullInfoMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String title = resultSet.getString("TITLE");
            long authorId = resultSet.getLong("AUTHOR_ID");
            long genreId = resultSet.getLong("GENRE_ID");
            String authorFirstName = resultSet.getString("AUTHOR_FIRSTNAME");
            String authorLastName = resultSet.getString("AUTHOR_LASTNAME");
            String genreName = resultSet.getString("GENRE_NAME");
            return new Book(id, title, new Author(authorId, authorFirstName, authorLastName), new Genre(genreId, genreName));
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String firstName = resultSet.getString("FIRSTNAME");
            String lastName = resultSet.getString("LASTNAME");
            return new Author(id, firstName, lastName);
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");
            return new Genre(id, name);
        }
    }

}
