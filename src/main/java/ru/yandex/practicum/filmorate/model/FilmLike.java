package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Getter;

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
