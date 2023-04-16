package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService implements FilmStorage {
    private final InMemoryFilmStorage inMemoryFilmStorage;

    public Film addLike(Film film) {
        film.addLike(film.getId());
        return film;
    }

    public Film unlike(Film film) {
        film.removeLike(film.getId());
        return film;
    }

    public Collection<Film> getMostPopularFilms() {
        return inMemoryFilmStorage
                .getFilms()
                .stream()
                .sorted(Comparator.comparingLong(f -> f.getLikes().size()))
                .limit(10)
                .collect(Collectors.toSet());
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
