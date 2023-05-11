package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaRatingServiceImpl implements MpaRatingService {

    private final MpaRatingStorage mpaRatingStorage;

    @Override
    public MpaRating getMpaRatingById(long genreId) {
        return mpaRatingStorage.getMpaRatingById(genreId);
    }

    @Override
    public Collection<MpaRating> GetMpaRatings() {
        return mpaRatingStorage.getMpaRatings();
    }
}
