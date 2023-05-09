package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.Collection;

public interface FilmLikeStorage {

    FilmLike createLike(FilmLike filmLike);

    Collection<FilmLike> getFilmLikes(long filmId);

    void deleteFilmLikeById(long filmLikeId);
}
