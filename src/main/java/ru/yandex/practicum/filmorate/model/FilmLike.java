package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FilmLike {
    
    @Positive
    private long id;

    @Positive
    private long userId;

    @Positive
    private long filmId;
}
