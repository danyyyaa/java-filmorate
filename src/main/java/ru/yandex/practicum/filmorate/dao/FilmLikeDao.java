package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.Collection;

public interface FilmLikeDao {

    FilmLike createLike(FilmLike filmLike);

    Collection<FilmLike> getFilmLikes(long filmId);

    void deleteLike(FilmLike filmLike);
}
