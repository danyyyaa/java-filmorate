package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GenreDbStorage implements GenreStorage {

    private final GenreDao genreDao;

    @Override
    public Collection<Genre> getGenres() {
        Collection<Genre> genres = genreDao.getGenres();
        log.info("Получены жанры: " + genres);
        return genres;
    }

    @Override
    public Genre getGenreById(long genreId) {
        Genre genre = genreDao.getGenreById(genreId).orElseThrow(GenreNotFoundException::new);
        log.info("Получен жанр: " + genre);
        return genre;
    }

    @Override
    public Collection<Genre> getGenresByFilmId(long filmId) {
        Collection<Genre> genres = genreDao.getGenresByFilmId(filmId);
        log.info("Получены жанры: " + genres);
        return genres;
    }
}
