package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class FilmLike {
    @PositiveOrZero
    private Long id;

    @PositiveOrZero
    private Long userId;

    @PositiveOrZero
    private Long filmId;
}
