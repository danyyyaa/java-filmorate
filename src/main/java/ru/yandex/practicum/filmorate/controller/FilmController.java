package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@RestController
public class FilmController {
    @PostMapping("/add-film")
    public void createFilm() {

    }

    @PutMapping("/update-film")
    public void updateFilm() {

    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        return null;
    }
}
