package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MpaRating {

    @Positive
    private long id;

    @NotBlank
    private String name;
}
