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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidationTests {
    private Validator validator;
    private UserController userController;
    private User user;
    Set<ConstraintViolation<User>> violations;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1)
                .email("mail@email.ru")
                .login("abc")
                .birthday(LocalDate.parse("11-11-2011", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .name("Sanya").
                build();

        userController = new UserController();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void userTestDefault() {
        userController.addUser(user);
        assertEquals(1, userController.getUsers().size());

        User testUser = User.builder()
                //.id(1)
                .email("mail@email.ru")
                .login("abc")
                .birthday(LocalDate.parse("11-11-2011", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .name("Sanya")
                .build();
    }

    @Test
    public void idMissedTest() {
        assertThrows(ValidationException.class, () -> userController.updateUser(user));
    }

    @Test
    public void emailWithoutAtTest() {
        user.setEmail("mail.ru");

        violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void emptyEmailTest() {
        user.setEmail("");

        violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void nullEmailTest() {
        user.setEmail(null);

        violations = validator.validate(user);
        assertEquals(1, violations.size());
    }
}
