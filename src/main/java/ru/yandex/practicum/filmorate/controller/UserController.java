package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final List<User> users = new ArrayList<>();

    @PostMapping()
    public void createUser(@RequestBody User user) {
        log.info("Получен запрос на создание пользователя");
        validation(user);
        users.add(user);
    }

    @PutMapping()
    public void updateUser(@RequestBody User user) {
        log.info("Получен запрос на обновление пользователя");
        validation(user);

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                users.set(i, user);
                return;
            }
        }
    }

    @GetMapping()
    public List<User> getUsers() {
        log.info("Получен запрос на получение пользователей");
        return users;
    }

    private void validation(User user) {
        boolean valid = !user.getEmail().contains("@")
                || user.getName().isBlank()
                || user.getName().contains(" ")
                || user.getBirthday().isAfter(LocalDate.now());

        boolean valid1 = user.getId() == null
                || user.getBirthday() == null
                || user.getLogin() == null
                || user.getEmail() == null
                || user.getName() == null;

        if (valid || valid1) {
            log.warn("Ошибка валидации");
            throw new ValidationException();
        }
    }
}
