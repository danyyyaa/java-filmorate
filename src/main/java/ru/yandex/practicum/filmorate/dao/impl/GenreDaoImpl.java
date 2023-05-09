package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void deleteGenreById(long id) {
        jdbcTemplate.update("DELETE FROM genre_t WHERE id = ?", id);
    }

    @Override
    public Collection<Genre> getGenres() {
        return jdbcTemplate.query("SELECT * FROM genre_t", new BeanPropertyRowMapper<>(Genre.class));
    }

    @Override
    public Collection<Genre> getGenresByFilmId(long id) {
        return jdbcTemplate.query("SELECT * FROM genre_t WHERE id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Genre.class));
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        return jdbcTemplate.query("SELECT * FROM genre_t WHERE id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Genre.class))
                .stream()
                .findAny();
    }
}
