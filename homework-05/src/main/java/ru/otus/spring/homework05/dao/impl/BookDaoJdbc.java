package ru.otus.spring.homework05.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework05.dao.BookDao;
import ru.otus.spring.homework05.domain.Author;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    private static final String GET_BOOK_INFO_SQL =
            "SELECT b.ID, b.TITLE, b.AUTHOR_ID, b.GENRE_ID, " +
                    "a.FIRSTNAME as AUTHOR_FIRSTNAME, a.LASTNAME as AUTHOR_LASTNAME, g.NAME as GENRE_NAME " +
                    "FROM BOOKS b " +
                    "INNER JOIN AUTHORS a ON b.AUTHOR_ID = a.ID " +
                    "INNER JOIN GENRES g ON b.GENRE_ID = g.ID " +
                    "WHERE b.AUTHOR_ID = a.ID AND b.GENRE_ID = g.ID";

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query(GET_BOOK_INFO_SQL, new BookFullInfoMapper());
    }

    @Override
    public Book getBookById(Long id) {
        String sql = GET_BOOK_INFO_SQL + " AND b.ID=:id";
        return jdbc.queryForObject(sql, Collections.singletonMap("id", id), new BookFullInfoMapper());
    }

    @Override
    public long createBook(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

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
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

        String sql = "UPDATE BOOKS SET TITLE=:title, AUTHOR_ID=:authorId, GENRE_ID=:genreId WHERE ID=:id";
        return jdbc.update(sql, params) > 0;
    }

    @Override
    public boolean deleteBook(Long id) {
        String sql = "DELETE FROM BOOKS WHERE ID = :id";
        return jdbc.update(sql, new MapSqlParameterSource("id", id)) > 0;
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
}
