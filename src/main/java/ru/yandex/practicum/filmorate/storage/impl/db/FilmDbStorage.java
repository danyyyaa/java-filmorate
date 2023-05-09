package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.FilmLikeDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.MpaRatingDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;


@Repository
@Primary
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final FilmDao filmDao;

    @Override
    public Collection<Film> getFilms() {
        return filmDao.getFilms();
    }

    @Override
    public Film updateFilm(Film film) {
        return filmDao.updateFilm(film);
    }

    @Override
    public Film createFilm(Film film) {
        return filmDao.createFilm(film);
    }

    @Override
    public Film getFilmById(long id) {
        return filmDao.getFilmById(id).get();
    }
}
