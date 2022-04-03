package ru.otus.spring.homework05.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework05.dao.GenreDao;
import ru.otus.spring.homework05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
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


    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");
            return new Genre(id, name);
        }
    }

}
