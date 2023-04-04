package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Getter
public class Film {
    private int id;
    private final String name;
    private final String description;
    private final Duration duration;
    private final LocalDate releaseDate;
}
