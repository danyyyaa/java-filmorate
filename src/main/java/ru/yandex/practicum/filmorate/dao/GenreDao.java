package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreDao {

    void deleteGenreById(long id);

    Collection<Genre> getGenres(long id);

    Collection<Genre> getGenresByFilmId(long id);
}
