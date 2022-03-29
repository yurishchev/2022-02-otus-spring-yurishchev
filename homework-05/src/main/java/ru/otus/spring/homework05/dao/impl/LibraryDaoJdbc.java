package ru.otus.spring.homework05.dao.impl;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework05.dao.LibraryDao;
import ru.otus.spring.homework05.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class LibraryDaoJdbc implements LibraryDao {
    private final JdbcOperations jdbc;

    public LibraryDaoJdbc(JdbcOperations jdbcOperations)
    {
        this.jdbc = jdbcOperations;
    }

    @Override
    public Book getBookById(long id) {
        return jdbc.queryForObject("select id, title, author_id, genre_id from BOOKS where id = ?", new BookMapper(), id);
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query("select id, title, author_id, genre_id from BOOKS", new BookMapper());
    }

    @Override
    public void createBook(Book book) {
        jdbc.update("insert into BOOKS (id, title, author_id, genre_id) values (?, ?, ?, ?)", book.getId(), book.getTitle(), book.getAuthor().getId());
    }

    @Override
    public void updateBook(Book book) {
        jdbc.update("delete from books (id, name) values (?, ?)", person.getId(), person.getName());
    }

    @Override
    public void deleteBook(Book book) {
        jdbc.update("delete from books (id, name) values (?, ?)", person.getId(), person.getName());
    }


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("TITLE");
            String authorId = resultSet.getString("AUTHOR_ID");
            String genreId = resultSet.getString("GENRE_ID");
            return new Book(id, title, authorId, genre);
        }
    }

}
