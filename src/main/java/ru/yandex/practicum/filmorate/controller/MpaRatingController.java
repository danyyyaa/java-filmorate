package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaRatingController {
    //private final MpaRatingService mpaRatingService;

    @GetMapping
    public Collection<MpaRating> getMpaRatings() {
        return null;
    }

    @GetMapping("/{mpaRatingId}")
    public MpaRating getMpaRatingById(@PathVariable long mpaRatingId) {
        return null;
    }
}
