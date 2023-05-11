package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Film getFilmById(long id);

    Collection<Film> getFilms();

    Film updateFilm(Film film);

    Film createFilm(Film film);
}
