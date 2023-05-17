package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmLikeDao;
import ru.yandex.practicum.filmorate.model.FilmLike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.constant.FilmLikeConstant.*;
import static ru.yandex.practicum.filmorate.constant.FilmLikeConstant.FILM_ID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilmLikeDaoImpl implements FilmLikeDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public FilmLike createLike(FilmLike filmLike) {
        Map<String, Object> keys = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(FILM_LIKE_TABLE)
                .usingColumns(USER_ID, FILM_ID)
                .usingGeneratedKeyColumns(ID)
                .executeAndReturnKeyHolder(Map.of(USER_ID, filmLike.getUserId(),
                        FILM_ID, filmLike.getFilmId()))
                .getKeys();
        assert keys != null;
        filmLike.setId((Long) keys.get(ID));
        return filmLike;
    }

    @Override
    public List<FilmLike> getFilmLikes(long filmId) {
        String sqlToFilmLikeTable = "SELECT * FROM film_like_t WHERE film_id = ? ";
        return jdbcTemplate.query(sqlToFilmLikeTable, (rs, rowNum) -> mapToFilmLike(rs), filmId)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void unlike(FilmLike filmLike) {
        String sqlToFilmLikeTable = "DELETE FROM film_like_t WHERE user_id = ? AND film_id = ?";
        jdbcTemplate.update(sqlToFilmLikeTable, filmLike.getUserId(),
                filmLike.getFilmId());
    }

    private FilmLike mapToFilmLike(ResultSet filmLikeRows) throws SQLException {
        return FilmLike.builder()
                .id(filmLikeRows.getLong(ID))
                .userId(filmLikeRows.getLong(USER_ID))
                .filmId(filmLikeRows.getLong(FILM_ID))
                .build();
    }
}
