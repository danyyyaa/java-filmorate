package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.time.DurationMin;
import ru.yandex.practicum.filmorate.validation.After;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/*@Getter
@Setter
@ToString*/
@Data
public class Film {

    @PositiveOrZero
    private long id;

    @NotBlank
    private String name;

    @Length(max = 200)
    private String description;

    @After("1895-12-28")
    private LocalDate releaseDate;

    @DurationMin(nanos = 1)
    private Duration duration;

    private Set<Long> likes = new HashSet<>();

    private Set<Long> genres = new HashSet<>();

    /*public Film(long id, String name, String description, LocalDate releaseDate, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(long id, String name, String description, LocalDate releaseDate, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = Duration.ofMinutes(duration);
    }*/
}
