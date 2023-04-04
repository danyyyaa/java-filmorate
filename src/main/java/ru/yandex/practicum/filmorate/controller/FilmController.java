package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private static int id = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping()
    public List<Film> getFilms() {
        log.info("Получен запрос на получение фильмов");
        //return films;
        return new ArrayList<>(films.values());
    }

    @PutMapping()
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        validation(film);

        for (Integer id : films.keySet()) {
            if (id.equals(film.getId())) {
                films.put(film.getId(), film);
            } else {
                throw new ValidationException();
            }
        }
        return film;
    }

    @PostMapping()
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        validation(film);
        int filmId = idGenerator();
        //System.out.println("DURATION = " + film.getDuration());

        film.setId(filmId);
        films.put(filmId, film);
        return film;
    }

    private void validation(Film film) {
        boolean valid = film.getName().isBlank()
                || film.getDescription().length() >= 200
                || film.getReleaseDate().isAfter(LocalDate.now())
                || film.getReleaseDate().isBefore(LocalDate.parse("28-12-1895", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                || film.getDuration().getSeconds() < 0;

        boolean valid1 = //film.getId() == null
                 film.getDuration() == null
                || film.getName() == null
                || film.getReleaseDate() == null
                || film.getDescription() == null;


        if (valid || valid1) {
            log.warn("Ошибка валидации");
            throw new ValidationException();
        }
    }

    private int idGenerator() {
        id++;
        return id;
    }
}
