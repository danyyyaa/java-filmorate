package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {

    Collection<Genre> getGenres();

    Genre getGenreById(long genreId);

    Collection<Genre> getGenresByFilmId(long filmId);
}
