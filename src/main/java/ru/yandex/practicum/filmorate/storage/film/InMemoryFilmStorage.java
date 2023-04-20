package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
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
        if (!films.containsKey(film.getId())) {
            log.error("Обновление несуществующего фильма: " + film);
            throw new FilmNotFoundException("Ошибка, обновление несуществующего фильма");
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
    public Film getFilmById(long id) {
        if (!films.containsKey(id)) {
            log.error("Получение несуществующего фильма:" + id);
            throw new FilmNotFoundException("Ошибка, фильм " + id + " не найден");
        }
        return films.get(id);
    }
}
