package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Override
    public Genre getGenreById(long genreId) {
        Genre genre = genreStorage.getGenreById(genreId).orElseThrow(GenreNotFoundException::new);
        log.info("Получен жанр: " + genre);
        return genre;
    }

    @Override
    public Collection<Genre> getGenres() {
        Collection<Genre> genres = genreStorage.getGenres();
        log.info("Получены жанры: " + genres);
        return genres;
    }
}
