package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
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
public class FilmDbStorage implements FilmStorage {

    private final FilmDao filmDao;
    private final MpaRatingStorage mpaRatingStorage;
    private final GenreStorage genreStorage;
    private final FilmGenreDao filmGenreDao;

    @Override
    public Collection<Film> getFilms() {
        var films = filmDao.getFilms();
        if (CollectionUtils.isEmpty(films)) {
            return films;
        }
        films.stream()
                .forEach(film -> {
                    film.setGenres((List<Genre>) genreStorage.getGenresByFilmId(film.getId()));
                    if (film.getMpa() != null & film.getMpa().getId() != null) {
                        film.setMpa(mpaRatingStorage.getMpaRatingById(film.getMpa().getId()));
                    }
                });
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

        return film;
    }

    @Override
    public Film getFilmById(long id) {
        Optional<Film> filmOpt = filmDao.getFilmById(id);
        if (filmOpt.isPresent()) {
            Film film = filmOpt.get();
            if (film.getMpa() != null & film.getMpa().getId() != null) {
                film.setMpa(mpaRatingStorage.getMpaRatingById(film.getMpa().getId()));
            }
            film.setGenres((List<Genre>) genreStorage.getGenresByFilmId(id));
            return film;
        }

        throw new FilmNotFoundException();
    }
}
