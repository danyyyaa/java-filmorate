package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.Collection;

public interface MpaRatingStorage {

    Collection<MpaRating> getMpaRatings();

    MpaRating getMpaRatingById(long mpaRatingId);
}
