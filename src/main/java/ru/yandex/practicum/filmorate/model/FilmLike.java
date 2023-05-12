package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class FilmLike {
    @Positive
    private long id;

    @Positive
    private long userId;

    @Positive
    private long filmId;
}
