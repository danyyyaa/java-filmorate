package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    User createUser(User user);

    Optional<User> updateUser(User user);

    Collection<User> getUsers();

    Optional<User> getUserById(long id);

    Collection<User> getUsersByIds(Collection<Long> ids);
}
