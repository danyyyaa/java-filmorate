package ru.yandex.practicum.filmorate.storage.impl.db;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

public class UserDbStorage implements UserStorage {
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public Collection<User> getUsers() {
        return null;
    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public Collection<User> getUsersByIds(Collection<Long> ids) {
        return null;
    }
}
