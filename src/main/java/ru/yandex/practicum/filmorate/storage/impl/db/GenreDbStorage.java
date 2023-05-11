package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final GenreDao genreDao;

    @Override
    public Collection<Genre> getGenres() {
        return genreDao.getGenres();
    }

    @Override
    public Genre getGenreById(long genreId) {
        Optional<Genre> genre = genreDao.getGenreById(genreId);
        if (genre.isPresent()) {
            return genre.get();
        }
        throw new GenreNotFoundException();
    }

    @Override
    public Collection<Genre> getGenresByFilmId(long filmId) {
        return genreDao.getGenresByFilmId(filmId);
    }
}
