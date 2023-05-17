package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;
import ru.yandex.practicum.filmorate.exception.MpaRatingNotFoundException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpaRatingServiceImpl implements MpaRatingService {

    private final MpaRatingStorage mpaRatingStorage;

    @Override
    public MpaRating getMpaRatingById(long mpaRatingId) {
        MpaRating mpaRating = mpaRatingStorage.getMpaRatingById(mpaRatingId)
                .orElseThrow(MpaRatingNotFoundException::new);
        log.info("Получен MPA рейтинг: " + mpaRating);
        return mpaRating;
    }

    @Override
    public Collection<MpaRating> getMpaRatings() {
        Collection<MpaRating> ratings = mpaRatingStorage.getMpaRatings();
        log.info("Получены MPA рейтинги: " + ratings);
        return ratings;
    }
}
