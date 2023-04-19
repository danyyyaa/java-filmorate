package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private static long id = 1;
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> getFilms() {
        log.info("Получение фильмов");
        return films.values();
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.get(film.getId()) == null) {
            log.error("Обновление несуществующего фильма");
            throw new FilmNotFoundException("Ошибка, обновление несуществующего фильма");
        }

        releaseDateCheck(film);

        films.put(film.getId(), film);
        log.info("Обновлен фильм: " + film);

        return film;
    }

    @Override
    public Film addFilm(Film film) {
        releaseDateCheck(film);
        film.setId(id);
        //film.setLikes(new HashSet<>());
        films.put(id++, film);
        log.info("Добавлен фильм: " + film);


        return film;
    }

    @Override
    public Film getFilmById(long id) {
        if (films.containsValue(id)) {
            log.error("Получение несуществующего фильма:" + id);
            throw new FilmNotFoundException("Ошибка, фильм " + id + " не найден");
        }
        return films.get(id);
    }

    private void releaseDateCheck(Film film) {
        if (film.getReleaseDate()
                .isBefore(LocalDate.parse("28-12-1895", DateTimeFormatter.ofPattern("dd-MM-yyyy")))) {
            log.error("Ошибка валидации");
            throw new ValidationException();
        }
    }
}
