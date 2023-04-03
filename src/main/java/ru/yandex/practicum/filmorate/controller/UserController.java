package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@RestController
public class UserController {
    @PostMapping("/create-user")
    public void createUser() {

    }

    @PutMapping("/update-user")
    public void updateUser() {

    }

    @GetMapping("users")
    public void getUsers() {

    }
}
