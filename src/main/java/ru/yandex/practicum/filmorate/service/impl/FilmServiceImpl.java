package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;


import java.util.ArrayList;
import java.util.Collection;


@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;

    private final FilmGenreDao filmGenreDao;

    @Override
    public Film getFilmById(long filmId) {
        Film film = filmStorage.getFilmById(filmId).orElseThrow(FilmNotFoundException::new);
        log.info("Получен фильм id = " + filmId);
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> films = filmStorage.getFilms();
        if (CollectionUtils.isEmpty(films)) {
            log.info("Получены фильмы: " + films);
            return films;
        }
        log.info("Получен пустой список фильмов");
        return new ArrayList<>(films);
    }

    @Override
    public Film updateFilm(Film film) {
        if (!isExist(film.getId())) {
            log.error("Ошибка, фильм не найден: " + film);
            throw new FilmNotFoundException();
        }
        return filmStorage.updateFilm(film).get();
    }

    @Override
    public Film createFilm(Film film) {
        filmStorage.createFilm(film);
        if (!CollectionUtils.isEmpty(film.getGenres())) {
            for (Genre genre : film.getGenres()) {
                filmGenreDao.linkGenreToFilm(film.getId(), genre.getId());
            }
        }

        log.info("Создан фильм: " + film);
        return film;
    }

    private boolean isExist(long id) {
        return filmStorage.getFilmById(id).isPresent();
    }
}
