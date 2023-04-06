package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.time.DurationMin;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class Film {
    @PositiveOrZero
    private int id;
    @NotBlank
    private String name;
    @Length(max = 200)
    private String description;
    @PastOrPresent
    private LocalDate releaseDate;
    @DurationMin(nanos = 1)
    private Duration duration;
}
