package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.filmorate.dao.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.*;


@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class FilmDbStorage implements FilmStorage {

    private final FilmDao filmDao;

    private final GenreStorage genreStorage;

    private final FilmGenreDao filmGenreDao;

    @Override
    public Collection<Film> getFilms() {
        return filmDao.getFilms();
    }

    @Override
    public Optional<Film> updateFilm(Film film) {
        filmGenreDao.deleteFilmGenres(film.getId());
        film = filmDao.updateFilm(film).get();
        if (!CollectionUtils.isEmpty(film.getGenres())) {
            for (Genre genre : film.getGenres()) {
                filmGenreDao.linkGenreToFilm(film.getId(), genre.getId());
            }
        }

        film.setGenres((List<Genre>) genreStorage.getGenresByFilmId(film.getId()));
        log.info("Обновлен фильм: " + film);
        return Optional.of(film);
    }

    @Override
    public Optional<Film> createFilm(Film film) {
        return filmDao.createFilm(film);
    }

    @Override
    public Optional<Film> getFilmById(long id) {
        return filmDao.getFilmById(id);
    }
}
