package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserDao {
    User createUser(User user);

    User updateUser(User user);

    Optional<User> getUserById(long userId);

    Collection<User> getUsers();

    Collection<User> getUsersByIds(Set<Long> ids);
}
