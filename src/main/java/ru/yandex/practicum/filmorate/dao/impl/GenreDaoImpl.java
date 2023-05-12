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

import static ru.yandex.practicum.filmorate.constant.GenreConstant.ID;

@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenres() {
        String sqlToGenreTable = "select * from genre_t";
        return jdbcTemplate.query(sqlToGenreTable, (rs, rowNum) -> mapToGenre(rs))
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        String sqlToGenreTable = "select * from genre_t where id = ? ";
        return jdbcTemplate.query(sqlToGenreTable, (rs, rowNum) -> mapToGenre(rs), id)
                .stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

    @Override
    public List<Genre> getGenresByFilmId(long filmId) {

        String sqlToGenreTable = "select gt.id, gt.name from genre_t as gt " +
                "join film_genre_t as fgt  on fgt.genre_id = gt.id " +
                "where fgt.film_id = ? ";
        return jdbcTemplate.query(sqlToGenreTable, (rs, rowNum) -> mapToGenre(rs), filmId)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Genre mapToGenre(ResultSet genreRows) throws SQLException {
        var genreId = genreRows.getLong(ID);
        if (genreId <= 0) {
            return null;
        }
        return new Genre(
                genreRows.getLong(GenreConstant.ID),
                genreRows.getString(GenreConstant.NAME));
    }
}
