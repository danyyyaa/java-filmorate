package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    User saveUser(User user);

    User updateUser(User user);

    Optional<User> getUserById(long id);

    Collection<User> getAllUsers();
}
