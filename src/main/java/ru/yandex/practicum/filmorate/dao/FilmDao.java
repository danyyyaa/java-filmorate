package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmDao {

    Optional<Film>  createFilm(Film film);

    Optional<Film>  updateFilm(Film film);

    Optional<Film> getFilmById(long filmId);

    Collection<Film> getFilms();
}
