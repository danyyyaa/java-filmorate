package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.time.DurationMin;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
public class Film {
    @PositiveOrZero
    private long id;
    @NotBlank
    private String name;
    @Length(max = 200)
    private String description;
    @PastOrPresent
    private LocalDate releaseDate;
    @DurationMin(nanos = 1)
    private Duration duration;
    private Set<Long> likes;

    public void addLike(long id) {
        likes.add(id);
    }

    public void removeLike(long id) {
        likes.remove(id);
    }
}
