package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTests {
    private UserController userController;
    private User user;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    public void setUp() {
        user = new User(1, "aa", "abc", "cc", LocalDate.of(2002, 11, 11));
        userController = new UserController(new UserServiceImpl(new InMemoryUserStorage()));
    }

    @Test
    public void userDefaultTest() {
        userController.createUser(user);
        assertEquals(1, userController.getUsers().size());
    }

    @Test
    public void idMissedTest() {
        assertThrows(UserNotFoundException.class, () -> userController.updateUser(user));
    }

    @Test
    public void emailWithoutAtTest() {
        user.setEmail("mail.ru");
        assertEquals(1, validator.validate(user).size());
    }

    @Test
    public void blankEmailTest() {
        user.setEmail(" ");
        assertEquals(2, validator.validate(user).size());
    }

    @Test
    public void nullEmailTest() {
        user.setEmail(null);
        assertEquals(1, validator.validate(user).size());
    }

    @Test
    public void blankLoginTest() {
        user.setLogin("");
        assertEquals(2, validator.validate(user).size());

        user.setLogin(" ");
        assertEquals(2, validator.validate(user).size());
    }

    @Test
    public void nullLoginTest() {
        user.setLogin(null);
        assertEquals(2, validator.validate(user).size());
    }

    @Test
    public void nullNameTest() {
        user.setName(null);
        assertEquals(user.getLogin(), "abc");
    }

    @Test
    public void blankNameTest() {
        user.setName("");
        assertEquals(user.getLogin(), "abc");

        user.setName(" ");
        assertEquals(user.getLogin(), "abc");
    }

    @Test
    public void birthdayInFutureTest() {
        user.setBirthday(LocalDate.parse("20-08-2446", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertEquals(2, validator.validate(user).size());
    }

    @Test
    public void nullRequestTest() {
        User user = null;
        assertThrows(NullPointerException.class, () -> userController.createUser(user));
    }

    @Test
    public void negativeIdTest() {
        user.setId(-1L);
        assertEquals(2, validator.validate(user).size());
    }
}
