package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmDao {
    Film saveFilm(Film film);

    Film updateFilm(Film film);

    Optional<Film> findFilmById(long filmId);

    Collection<Film> findAllFilms();
}
