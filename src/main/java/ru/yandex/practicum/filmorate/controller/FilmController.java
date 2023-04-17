package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @GetMapping()
    public Collection<Film> getFilms(@RequestParam(required = false) Long id) {
        if (id == null) {
            return filmService.getFilms();
        }
        return Collections.singleton(filmService.getFilmById(id));
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlike(@PathVariable int id, @PathVariable int userId) {
    }

    @GetMapping("/popular?count={count}")
    public Collection<Film> getMostPopularFilms(@PathVariable Optional<Integer> count) {
        if (count.isPresent()) {
            return filmService.getMostPopularFilms(count.get());
        }
        return filmService.getMostPopularFilms(10);
    }
}
