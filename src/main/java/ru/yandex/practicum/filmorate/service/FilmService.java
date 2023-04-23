package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Film getFilmById(long id);

    void addLike(long filmId, long userId);

    void unlike(long filmId, long userId);

    Collection<Film> getMostPopularFilms(Integer count);

    Collection<Film> getFilms();

    Film updateFilm(Film film);

    Film createFilm(Film film);
}
