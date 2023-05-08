package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaRatingDao;
import ru.yandex.practicum.filmorate.model.MpaRating;


import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MpaRatingDaoImpl implements MpaRatingDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<MpaRating> getMpaRatingById(long id) {
        return jdbcTemplate.query("SELECT * FROM mpa_rating_t WHERE id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(MpaRating.class))
                .stream()
                .findAny();
    }

    @Override
    public Collection<MpaRating> getMpaRatings() {
        return jdbcTemplate.query("SELECT * FROM mpa_rating_t", new BeanPropertyRowMapper<>(MpaRating.class));
    }
}
