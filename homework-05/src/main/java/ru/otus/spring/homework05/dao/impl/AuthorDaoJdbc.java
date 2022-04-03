package ru.otus.spring.homework05.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework05.dao.AuthorDao;
import ru.otus.spring.homework05.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
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


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String firstName = resultSet.getString("FIRSTNAME");
            String lastName = resultSet.getString("LASTNAME");
            return new Author(id, firstName, lastName);
        }
    }

}
