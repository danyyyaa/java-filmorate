package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
public class FilmLike {
    @PositiveOrZero
    private long id;

    @PositiveOrZero
    private long UserId;

    @PositiveOrZero
    private long filmId;
}
