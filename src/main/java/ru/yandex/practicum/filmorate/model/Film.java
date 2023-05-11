package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validation.After;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/*@Getter
@Setter
@ToString*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @PositiveOrZero
    private long id;

    @NotBlank
    private String name;

    @Length(max = 200)
    private String description;

    @After("1895-12-28")
    private LocalDate releaseDate;

    private Integer duration;

    private MpaRating mpaRating;

    private Set<Long> likes = new HashSet<>();

    private Set<Genre> genres;



    public Film(long id, String name, String description, LocalDate releaseDate,
                Integer duration, MpaRating mpaRating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpaRating = mpaRating;
    }
}
