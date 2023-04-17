package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService implements FilmStorage {
    private final InMemoryFilmStorage inMemoryFilmStorage;

    public Film getFilmById(long id) {
        if (!inMemoryFilmStorage.getMap().containsKey(id)) {
            throw new FilmNotFoundException("Film not found.");
        }
        return inMemoryFilmStorage.getMap().get(id);
    }

    public void addLike(int id, int userId) {
        inMemoryFilmStorage.getMap().get(id).addLike(userId);
    }

    public void unlike(int id, int userId) {
        inMemoryFilmStorage.getMap().get(id).removeLike(userId);
    }

    public Collection<Film> getMostPopularFilms(int count) {
        return inMemoryFilmStorage
                .getFilms()
                .stream()
                .sorted(Comparator.comparingLong(f -> f.getLikes().size()))
                .limit(count)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    @Override
    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    @Override
    public Film addFilm(Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }
}
