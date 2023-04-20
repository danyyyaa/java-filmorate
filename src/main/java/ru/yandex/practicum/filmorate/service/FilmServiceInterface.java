package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmServiceInterface {
    Film getFilmById(long id);

    void addLike(long id, long userId);

    void unlike(long id, long userId);

    Collection<Film> getMostPopularFilms(long count);

    Collection<Film> getFilms();

    Film updateFilm(Film film);

    Film createFilm(Film film);
}
