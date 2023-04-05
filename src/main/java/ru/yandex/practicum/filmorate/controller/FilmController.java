package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private static int id = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping()
    public Collection<Film> getFilms() {
        log.info("Получен запрос на получение фильмов");
        log.info("Получение фильмов");

        return films.values();
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        validation(film);

        if (films.get(film.getId()) == null) {
            log.warn("Обновление несуществующего фильма");
            throw new ValidationException();
        }

        films.put(film.getId(), film);
        log.info("Фильм обновлен");

        return film;
    }

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        validation(film);
        int filmId = idGenerator();
        film.setId(filmId);
        films.put(filmId, film);
        log.info("Фильм добавлен");

        return film;
    }

    private void validation(Film film) {
        boolean valid = film.getDescription().length() >= 200
                || film.getReleaseDate().isAfter(LocalDate.now())
                || film.getReleaseDate().
                isBefore(LocalDate.parse("28-12-1895", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                || film.getDuration().getSeconds() < 0;

        if (valid) {
            log.warn("Ошибка валидации");
            throw new ValidationException();
        }
    }

    private int idGenerator() {
        return id++;
    }
}
