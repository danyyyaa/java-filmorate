package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Film {
    private int id;
    private final String name;
    private final String description;

    @Value("${your.amazing.duration}")
    private Duration duration;
    private final LocalDate releaseDate;

    public Film(String name, String description, Duration duration, LocalDate releaseDate) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(name, film.name) && Objects.equals(description, film.description) && Objects.equals(duration, film.duration) && Objects.equals(releaseDate, film.releaseDate);
    }

    public void setDuration(long duration) {
        this.duration = Duration.ofSeconds(duration);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, duration, releaseDate);
    }
}
