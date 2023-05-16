package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.MpaRatingDao;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MpaRatingDbStorage implements MpaRatingStorage {

    private final MpaRatingDao mpaRatingDao;

    @Override
    public Collection<MpaRating> getMpaRatings() {
        return mpaRatingDao.getMpaRatings();
    }

    @Override
    public Optional<MpaRating> getMpaRatingById(long mpaRatingId) {
        return mpaRatingDao.getMpaRatingById(mpaRatingId);
    }
}
