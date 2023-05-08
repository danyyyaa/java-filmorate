package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film createFilm(Film film) {
        jdbcTemplate.update("INSERT INTO film_t VALUES(?, ?, ?, ?, ?)",
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update("UPDATE film_t SET id = ?, name = ?, description = ?, releaseDate = ?, duration = ?",
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());
        return film;
    }

    @Override
    public Optional<Film> getFilmById(long filmId) {
        return jdbcTemplate.query("SELECT * FROM film_t WHERE id = ?",
                        new Object[]{filmId},
                        new BeanPropertyRowMapper<>(Film.class))
                .stream()
                .findAny();
    }

    @Override
    public Collection<Film> getFilms() {
        return jdbcTemplate.query("SELECT * FROM user_t ", new BeanPropertyRowMapper<>(Film.class));
    }
}
