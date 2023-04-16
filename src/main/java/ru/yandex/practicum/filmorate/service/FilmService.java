package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService implements FilmStorage {
    private final InMemoryFilmStorage inMemoryFilmStorage;

    public Film addLike(Film film) {
        return null;
    }

    public Film unlike(Film film) {
        return null;
    }

    public Collection<Film> getMostPopularFilms() {
        return null;
    }

    @Override
    public Collection<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    @Override
    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    @Override
    public Film addFilm(Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }
}
