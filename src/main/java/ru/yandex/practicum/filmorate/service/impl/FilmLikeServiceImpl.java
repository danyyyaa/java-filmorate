package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmLike;
import ru.yandex.practicum.filmorate.service.FilmLikeService;
import ru.yandex.practicum.filmorate.storage.FilmLikeStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmLikeServiceImpl implements FilmLikeService {

    private final FilmLikeStorage filmLikeStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public void createLike(long filmId, long userId) {
        filmLikeStorage.createLike(FilmLike.builder()
                .filmId(filmId)
                .userId(userId)
                .build());
    }

    @Override
    public void unlike(long filmId, long userId) {
        userStorage.getUserById(userId);
        filmLikeStorage.unlike(FilmLike.builder()
                .filmId(filmId)
                .userId(userId)
                .build());
    }

    @Override
    public Collection<Film> getMostPopularFilms(int count) {
        Comparator<Film> comparator = Comparator.comparingInt((Film film) -> film.getLikes().size());
        return filmStorage.getFilms()
                .stream()
                .peek(el -> {
                    List<FilmLike> likes = (List<FilmLike>) filmLikeStorage.getFilmLikes(el.getId());
                    if (!CollectionUtils.isEmpty(likes)) {
                        el.setLikes(likes.stream()
                                .map(FilmLike::getFilmId)
                                .collect(Collectors.toList()));
                    }
                })
                .sorted(comparator.reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}

