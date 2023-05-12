package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.filmorate.dao.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.*;


@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class FilmDbStorage implements FilmStorage {

    private final FilmDao filmDao;
    private final MpaRatingStorage mpaRatingStorage;
    private final GenreStorage genreStorage;
    private final FilmGenreDao filmGenreDao;

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> films = filmDao.getFilms();
        if (CollectionUtils.isEmpty(films)) {
            log.info("Получены фильмы: " + films);
            return films;
        }
        films.forEach(film -> {
            film.setGenres((List<Genre>) genreStorage.getGenresByFilmId(film.getId()));
            film.setMpa(mpaRatingStorage.getMpaRatingById(film.getMpa().getId()));
        });
        log.info("Получен пустой список фильмов");
        return new ArrayList<>(films);
    }

    @Override
    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        filmGenreDao.deleteFilmGenres(film.getId());
        film = filmDao.updateFilm(film);
        if (!CollectionUtils.isEmpty(film.getGenres())) {
            for (Genre genre : film.getGenres()) {
                filmGenreDao.linkGenreToFilm(film.getId(), genre.getId());
            }
        }

        film.setGenres((List<Genre>) genreStorage.getGenresByFilmId(film.getId()));
        log.info("Обновлен фильм: " + film);
        return film;
    }

    @Override
    public Film createFilm(Film film) {
        filmDao.createFilm(film);
        if (!CollectionUtils.isEmpty(film.getGenres())) {
            for (Genre genre : film.getGenres()) {
                filmGenreDao.linkGenreToFilm(film.getId(), genre.getId());
            }
        }

        log.info("Создан фильм: " + film);
        return film;
    }

    @Override
    public Film getFilmById(long id) {
        Optional<Film> filmOpt = filmDao.getFilmById(id);
        if (filmOpt.isPresent()) {
            Film film = filmOpt.get();
            film.setMpa(mpaRatingStorage.getMpaRatingById(film.getMpa().getId()));
            film.setGenres((List<Genre>) genreStorage.getGenresByFilmId(id));
            log.info("Получен фильм id = " + id);
            return film;
        }

        log.error("Ошибка, фильм id = " + id + " не найден");
        throw new FilmNotFoundException();
    }
}
