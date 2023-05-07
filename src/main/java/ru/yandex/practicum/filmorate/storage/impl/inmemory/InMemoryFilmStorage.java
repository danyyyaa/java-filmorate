package ru.yandex.practicum.filmorate.storage.impl.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private static long id = 1L;
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> getFilms() {
        log.info("Получение фильмов: " + films.values());
        return films.values();
    }

    @Override
    public Film updateFilm(Film film) {
        if (!isExist(film.getId())) {
            log.error("Обновление несуществующего фильма: " + film);
            throw new FilmNotFoundException("Ошибка, обновление несуществующего фильма: " + film);
        }

        films.put(film.getId(), film);
        log.info("Обновлен фильм: " + film);

        return film;
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(id);
        films.put(id++, film);
        log.info("Добавлен фильм: " + film);

        return film;
    }

    @Override
    public Film getFilmById(long filmId) {
        if (!isExist(filmId)) {
            log.error("Получение несуществующего фильма:" + filmId);
            throw new FilmNotFoundException("Ошибка, фильм " + filmId + " не найден");
        }
        return films.get(filmId);
    }

    private boolean isExist(long filmId) {
        return films.containsKey(filmId);
    }
}
