package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
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
        log.info("Получение фильмов");
        return films.values();
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        releaseDateCheck(film);

        if (films.get(film.getId()) == null) {
            log.error("Обновление несуществующего фильма");
            throw new ValidationException();
        }

        films.put(film.getId(), film);
        log.info("Обновлен фильм: " + film);

        return film;
    }

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        releaseDateCheck(film);
        film.setId(id);
        films.put(id++, film);
        log.info("Добавлен фильм: " + film);

        return film;
    }

    private void releaseDateCheck(Film film) {
        if (film.getReleaseDate()
                .isBefore(LocalDate.parse("28-12-1895", DateTimeFormatter.ofPattern("dd-MM-yyyy")))) {
            log.error("Ошибка валидации");
            throw new ValidationException();
        }
    }
}
