package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.Collection;

public interface FilmLikeDao {

    FilmLike createLike(FilmLike filmLike);

    Collection<FilmLike> getLikes(long filmId);

    void deleteLike(long filmId);
}
