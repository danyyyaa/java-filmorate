package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmLikeDao;
import ru.yandex.practicum.filmorate.model.FilmLike;
import ru.yandex.practicum.filmorate.storage.FilmLikeStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Repository
@Primary
@RequiredArgsConstructor
public class FilmLikeDbStorage implements FilmLikeStorage {
    private final FilmLikeDao filmLikeDao;
    private final FilmStorage filmStorage;

    @Override
    public FilmLike createLike(FilmLike filmLike) {
        return filmLikeDao.createLike(filmLike);
    }

    @Override
    public Collection<FilmLike> getFilmLikes(long filmId) {
        return filmLikeDao.getFilmLikes(filmId);
    }

    @Override
    public void deleteLike(FilmLike filmLike) {
        filmStorage.getFilmById(filmLike.getFilmId());
        filmLikeDao.deleteLike(filmLike);
    }
}
