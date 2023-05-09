package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.MpaRatingDao;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;

@Repository
@Primary
@RequiredArgsConstructor
public class MpaRatingDbStorage implements MpaRatingStorage {
    private final MpaRatingDao mpaRatingDao;

    @Override
    public Collection<MpaRating> getMpaRatings() {
        return mpaRatingDao.getMpaRatings();
    }

    @Override
    public MpaRating getMpaRatingById(long mpaRatingId) {
        return mpaRatingDao.getMpaRatingById(mpaRatingId).get();
    }
}
