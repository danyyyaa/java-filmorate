package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;


@Data
public class Genre {
    @PositiveOrZero
    private long id;

    @NotBlank
    private String name;

}
