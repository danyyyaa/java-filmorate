package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final UserDao userDao;

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public Optional<User> updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public Collection<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public Collection<User> getUsersByIds(Collection<Long> ids) {
        return userDao.getUsersByIds((Set<Long>) ids);
    }

    @Override
    public Optional<User> getUserById(long userId) {
        return userDao.getUserById(userId);
    }
}
