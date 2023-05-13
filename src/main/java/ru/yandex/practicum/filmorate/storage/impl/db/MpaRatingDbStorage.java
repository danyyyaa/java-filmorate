package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.MpaRatingDao;
import ru.yandex.practicum.filmorate.exception.MpaRatingNotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MpaRatingDbStorage implements MpaRatingStorage {

    private final MpaRatingDao mpaRatingDao;

    @Override
    public Collection<MpaRating> getMpaRatings() {
        Collection<MpaRating> ratings = mpaRatingDao.getMpaRatings();
        log.info("Получены MPA рейтинги: " + ratings);
        return ratings;
    }

    @Override
    public MpaRating getMpaRatingById(long mpaRatingId) {
        if (!isExist(mpaRatingId)) {
            log.error("Ошибка, такого MPA рейтинга нету: id = " + mpaRatingId);
            throw new MpaRatingNotFoundException();
        }

        MpaRating mpaRating = mpaRatingDao.getMpaRatingById(mpaRatingId).get();
        log.info("Получен MPA рейтинг: " + mpaRating);
        return mpaRating;
    }

    private boolean isExist(long mpaRatingId) {
        return mpaRatingDao.getMpaRatingById(mpaRatingId).isPresent();
    }
}
