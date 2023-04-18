package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmServiceInterface;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmServiceInterface filmService;

    @GetMapping()
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable long id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlike(@PathVariable int id, @PathVariable int userId) {
    }

    @GetMapping("/popular")
    public Collection<Film> getMostPopularFilms(@RequestParam(required = false) Optional<Integer> count) {
        if (count.isPresent()) {
            return filmService.getMostPopularFilms(count.get());
        }
        return filmService.getMostPopularFilms(10);
    }
}
