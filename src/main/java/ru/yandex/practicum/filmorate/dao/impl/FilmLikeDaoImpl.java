package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmLikeDao;
import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class FilmLikeDaoImpl implements FilmLikeDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public FilmLike createLike(FilmLike filmLike) {
        jdbcTemplate.update("INSERT INTO film_like_t VALUES(?, ?, ?)",
                filmLike.getId(),
                filmLike.getUserId(),
                filmLike.getFilmId());
        return filmLike;
    }

    @Override
    public Collection<FilmLike> getLikes(long filmId) {
        return jdbcTemplate.query("SELECT * FROM film_like_t", new BeanPropertyRowMapper<>(FilmLike.class));
    }

    @Override
    public void deleteLike(long filmId) {
        jdbcTemplate.update("DELETE FROM film_like_t WHERE id = ?", filmId);
    }
}
