package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Override
    public Genre getGenreById(long genreId) {
        return genreStorage.getGenreById(genreId);
    }

    @Override
    public Collection<Genre> getGenres() {
        return genreStorage.getGenres();
    }
}
