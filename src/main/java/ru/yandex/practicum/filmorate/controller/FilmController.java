package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @GetMapping()
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable long filmId) {
        return filmService.getFilmById(filmId);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlike(@PathVariable long id, @PathVariable long userId) {
        filmService.unlike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getMostPopularFilms(@RequestParam(required = false) Optional<Integer> count) {
        if (count.isPresent()) {
            return filmService.getMostPopularFilms(count.get());
        }
        return filmService.getMostPopularFilms(10);
    }
}
