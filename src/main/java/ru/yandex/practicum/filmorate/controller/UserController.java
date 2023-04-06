package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
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
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        user.setId(id);
        users.put(id++, user);
        log.info("Добавлен пользователь: " + user);

        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        if (users.get(user.getId()) == null) {
            log.error("Обновление несуществующего пользователя");
            throw new ValidationException();
        }

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);
        log.info("Обновлен пользователь: " + user);

        return user;
    }

    @GetMapping()
    public Collection<User> getUsers() {
        log.info("Получение пользователей");
        return users.values();
    }
}
