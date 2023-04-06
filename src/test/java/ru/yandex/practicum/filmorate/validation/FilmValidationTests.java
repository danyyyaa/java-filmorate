package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class FilmValidationTests {
    private FilmController filmController;
    private Film film;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    public void setUp() {
        film = Film.builder()
                .name("aaa")
                .description("bbb")
                .releaseDate(LocalDate.parse("11-11-2011", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .duration(Duration.ofMinutes(120))
                .build();

        filmController = new FilmController();
    }

    @Test
    public void filmDefaultTest() {
        filmController.addFilm(film);
        assertEquals(1, filmController.getFilms().size());
    }

    @Test
    public void nullRequestTest() {
        Film film = null;
        assertThrows(NullPointerException.class, () -> filmController.addFilm(film));

    }

    @Test
    public void idMissedTest() {
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film));
    }

    @Test
    public void blankNameTest() {
        film.setName(null);
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void nullNameTest() {
        film.setName("");
        assertEquals(1, validator.validate(film).size());

        film.setName(" ");
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void moreThanTwoHundredCharactersDescriptionTest() {
        film.setDescription("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void releaseDateIsBefore28_1895Test() {
        film.setReleaseDate(LocalDate.parse("28-12-1894", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film));

    }

    @Test
    public void releaseDateInFutureTest() {
        film.setReleaseDate(LocalDate.parse("28-12-2200", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void negativeDurationTest() {
        film.setDuration(Duration.ofSeconds(-200));
        assertEquals(1, validator.validate(film).size());
    }
}
