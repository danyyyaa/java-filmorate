package ru.yandex.practicum.filmorate.validation;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
@SpringBootTest
public class FilmValidationTests {
    private FilmController filmController;
    private Film film;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    public void setUp() {
        film = new Film(1, "name", "aa",
                LocalDate.of(2002, 2, 2), 2, new MpaRating(1L, "name"));
    }

    @Test
    public void nullRequestTest() {
        Film film = null;
        assertThrows(NullPointerException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void idMissedTest() {
        assertThrows(NullPointerException.class, () -> filmController.updateFilm(film));
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
        assertThrows(NullPointerException.class, () -> filmController.updateFilm(film));
    }

    @Test
    public void negativeDurationTest() {
        film.setDuration(-200);
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void negativeIdTest() {
        film.setId(-1L);
        assertEquals(1, validator.validate(film).size());
    }
}
