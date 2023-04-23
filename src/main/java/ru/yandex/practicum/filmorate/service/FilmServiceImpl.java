package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film getFilmById(long filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public void addLike(long filmId, long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        film.getLikes().add(user.getId());
    }

    public void unlike(long filmId, long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        film.getLikes().remove(user.getId());
    }

    public Collection<Film> getMostPopularFilms(Integer count) {
        Comparator<Film> comparator = Comparator.comparingInt(f -> f.getLikes().size());
        return filmStorage
                .getFilms()
                .stream()
                .sorted(comparator.reversed())
                .limit(count)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @Override
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    @Override
    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }
}
