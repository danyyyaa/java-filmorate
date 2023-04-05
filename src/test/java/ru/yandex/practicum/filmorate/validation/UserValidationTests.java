package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTests {
    private UserController userController;
    private User user;
    Set<ConstraintViolation<User>> violations;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .email("mail@email.ru")
                .login("abc")
                .birthday(LocalDate.parse("11-11-2011", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .name("Sanya").
                build();

        userController = new UserController();
    }

    @Test
    public void userDefaultTest() {
        userController.addUser(user);
        assertEquals(1, userController.getUsers().size());
    }

    @Test
    public void idMissedTest() {
        assertThrows(ValidationException.class, () -> userController.updateUser(user));
    }

    @Test
    public void emailWithoutAtTest() {
        user.setEmail("mail.ru");
        assertEquals(1, validator.validate(user).size());
    }

    @Test
    public void blankEmailTest() {
        user.setEmail(" ");
        violations = validator.validate(user);
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
        assertEquals(1, validator.validate(user).size());

        user.setLogin(" ");
        assertEquals(1, validator.validate(user).size());
    }

    @Test
    public void nullLoginTest() {
        user.setLogin(null);
        assertEquals(1, validator.validate(user).size());
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
        assertEquals(1, validator.validate(user).size());
    }
}
