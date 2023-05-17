package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.constant.GenreConstant;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenres() {
        String sqlToGenreTable = "SELECT * FROM genre_t";
        return jdbcTemplate.query(sqlToGenreTable, (rs, rowNum) -> mapToGenre(rs))
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        String sqlToGenreTable = "SELECT * FROM genre_t WHERE id = ? ";
        return jdbcTemplate.query(sqlToGenreTable, (rs, rowNum) -> mapToGenre(rs), id)
                .stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

    @Override
    public List<Genre> getGenresByFilmId(long filmId) {
        String sqlToGenreTable = "SELECT gt.id, gt.name FROM genre_t AS gt " +
                "JOIN film_genre_t AS fgt ON fgt.genre_id = gt.id " +
                "WHERE fgt.film_id = ? ";
        return jdbcTemplate.query(sqlToGenreTable, (rs, rowNum) -> mapToGenre(rs), filmId)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Genre mapToGenre(ResultSet genreRows) throws SQLException {
        return Genre.builder()
                .id(genreRows.getLong(GenreConstant.ID))
                .name(genreRows.getString(GenreConstant.NAME))
                .build();
    }
}
