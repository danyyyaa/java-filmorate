package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.constant.MpaRatingConstant;
import ru.yandex.practicum.filmorate.dao.MpaRatingDao;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.constant.MpaRatingConstant.ID;

@Component
@RequiredArgsConstructor
public class MpaRatingDaoImpl implements MpaRatingDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MpaRating> getMpaRatings() {
        String sqlToMpaRatingTable = "select * from mpa_rating_t";
        return jdbcTemplate.query(sqlToMpaRatingTable, (rs, rowNum) -> mapToMpaRating(rs))
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MpaRating> getMpaRatingById(long id) {
        String sqlToMpaRatingTable = "select * from mpa_rating_t where id = ? ";
        return jdbcTemplate.query(sqlToMpaRatingTable, (rs, rowNum) -> mapToMpaRating(rs), id)
                .stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

    private MpaRating mapToMpaRating(ResultSet mpaRatingRows) throws SQLException {
        var mpaRaringId = mpaRatingRows.getLong(ID);
        if (mpaRaringId <= 0) {
            return null;
        }
        return new MpaRating(
                mpaRatingRows.getLong(ID),
                mpaRatingRows.getString(MpaRatingConstant.NAME));
    }
}
