package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.constant.FilmConstant.*;
import static ru.yandex.practicum.filmorate.constant.FilmConstant.ID;

@Component
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film createFilm(Film film) {
        Map<String, Object> keys = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(FILM_TABLE)
                .usingColumns(NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_RATING_ID)
                .usingGeneratedKeyColumns(ID)
                .executeAndReturnKeyHolder(Map.of(NAME, film.getName(),
                        DESCRIPTION, film.getDescription(),
                        RELEASE_DATE, java.sql.Date.valueOf(film.getReleaseDate()),
                        DURATION, film.getDuration(),
                        MPA_RATING_ID, film.getMpa().getId()))
                .getKeys();
        assert keys != null;
        film.setId((Long) keys.get(ID));
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sql =
                "UPDATE film_t SET id = ?, name = ?, description = ?, release_date = ?, duration = ?, mpa_rating_id = ?"
                        + " WHERE id = ? ";
        jdbcTemplate.update(sql,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return film;
    }

    @Override
    public Optional<Film> getFilmById(long id) {
        String sqlToFilmTable = "SELECT * FROM film_t WHERE id = ? ";
        return jdbcTemplate.query(sqlToFilmTable, (rs, rowNum) -> mapToFilm(rs), id)
                .stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

    @Override
    public List<Film> getFilms() {
        String sqlToFilmTable = "SELECT * FROM film_t";
        return jdbcTemplate.query(sqlToFilmTable, (rs, rowNum) -> mapToFilm(rs))
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Film mapToFilm(ResultSet filmRows) throws SQLException {
        return Film.builder()
                .id(filmRows.getLong(ID))
                .name(filmRows.getString(NAME))
                .description(filmRows.getString(DESCRIPTION))
                .releaseDate(filmRows.getDate(RELEASE_DATE).toLocalDate())
                .duration(filmRows.getInt(DURATION))
                .mpa(MpaRating.builder()
                        .id(filmRows.getLong(MPA_RATING_ID))
                        .build())
                .likes(new ArrayList<>())
                .build();
    }
}
