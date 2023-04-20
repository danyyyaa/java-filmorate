package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService implements FilmServiceInterface {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film getFilmById(long id) {
        return filmStorage.getFilmById(id) ;
    }

    public void addLike(long id, long userId) {
        filmStorage.getFilmById(id).addLike(userStorage.getUserById(userId).getId());
    }

    public void unlike(long  id, long  userId) {
        filmStorage.getFilmById(id).removeLike(userStorage.getUserById(userId).getId());
    }

    public Collection<Film> getMostPopularFilms(long count) {
        return filmStorage
                .getFilms()
                .stream()
                .sorted(Comparator.comparingLong(f -> f.getLikes().size()))
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
    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }
}
