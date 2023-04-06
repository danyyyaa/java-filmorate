package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private static int id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя");

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        int userId = idGenerator();
        user.setId(userId);
        users.put(userId, user);
        log.info("Пользователь добавлен");

        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя");

        if (users.get(user.getId()) == null) {
            log.warn("Обновление несуществующего пользователя");
            throw new ValidationException();
        }

        if (user.getName() == null) {
            user.setName(user.getLogin());
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

    private int idGenerator() {
        return id++;
    }
}
