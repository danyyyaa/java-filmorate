package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;


@Data
public class Friendship {
    @PositiveOrZero
    private long id;

    @PositiveOrZero
    private long userId;

    @PositiveOrZero
    private long friendId;

    private Boolean status;
}
