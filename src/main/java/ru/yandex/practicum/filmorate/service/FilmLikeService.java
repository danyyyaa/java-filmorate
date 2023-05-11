package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmLikeService {

    void createLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    Collection<Film> getMostPopular(long count);
}
