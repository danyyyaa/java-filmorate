package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private static int id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping()
    public User createUser(@RequestBody User user) {
        log.info("Получен запрос на создание пользователя");
        validation(user);
        int userId = idGenerator();
        user.setId(userId);
        users.put(userId, user);
        log.info("Пользователь добавлен");
        return user;
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {
        log.info("Получен запрос на обновление пользователя");
        validation(user);

        if (users.get(user.getId()) == null) {
            log.warn("Обновление несуществующего пользователя");
            throw new ValidationException();
        }
        users.put(user.getId(), user);
        log.info("Пользователь обновлен");
        return user;
    }

    @GetMapping()
    public Collection<User> getUsers() {
        log.info("Получен запрос на получение пользователей");
        log.info("Получение пользователей");
        return users.values();
    }

    private void validation(User user) {
        boolean valid = !user.getEmail().contains("@")
                || user.getEmail().isBlank()
                || user.getLogin().isBlank()
                || user.getBirthday().isAfter(LocalDate.now());

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        if (valid) {
            log.warn("Ошибка валидации");
            throw new ValidationException();
        }
    }

    private int idGenerator() {
        return id++;
    }
}
