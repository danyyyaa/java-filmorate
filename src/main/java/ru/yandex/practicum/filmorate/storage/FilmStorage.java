package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    /*Collection<Film> getFilms();

    Film updateFilm(Film film);

    Film createFilm(Film film);

    Film getFilmById(long id);*/

    Collection<Film> getFilms();

    Optional<Film> updateFilm(Film film);

    Optional<Film> createFilm(Film film);

    Optional<Film> getFilmById(long id);
}
