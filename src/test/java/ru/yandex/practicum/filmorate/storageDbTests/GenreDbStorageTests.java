package ru.yandex.practicum.filmorate.storageDbTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTests {

    private final GenreStorage genreStorage;

    private final FilmService filmService;


    @Test
    public void getGenreByIdTest() {
        Optional<Genre> genre = genreStorage.getGenreById(1);
        assertThat(genre)
                .isPresent()
                .hasValueSatisfying(genre1 -> assertThat(genre1).hasFieldOrProperty("id"));
    }

    @Test
    public void getGenresTest() {
        Optional<Genre> genre = genreStorage.getGenres().stream().findFirst();
        assertThat(genre)
                .isPresent()
                .hasValueSatisfying(genre1 -> assertThat(genre1).hasFieldOrProperty("name"));
    }

    @Test
    public void getGenresByFilmIdTest() {
        Film film = Film.builder()
                .id(1L)
                .name("name")
                .description("description")
                .duration(1)
                .genres(List.of(Genre.builder()
                        .id(1L)
                        .name("Комедия")
                        .build()))
                .likes(new ArrayList<>())
                .releaseDate(LocalDate.of(1997, 5, 5))
                .mpa(MpaRating.builder()
                        .id(1L)
                        .name("name")
                        .build())
                .build();
        filmService.createFilm(film);

        Optional<Genre> genre = genreStorage.getGenresByFilmId(film.getId()).stream().findFirst();
        assertThat(genre)
                .isPresent()
                .hasValueSatisfying(genre1 -> assertThat(genre1).hasFieldOrProperty("name"));
    }
}
