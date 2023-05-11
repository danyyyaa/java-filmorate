package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final UserDao userDao;

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        return userDao.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        if (!isExist(user.getId())) {
            throw new UserNotFoundException();
        }
        userDao.updateUser(user);
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public User getUserById(long userId) {
        if (!isExist(userId)) {
            throw new UserNotFoundException();
        }
        return userDao.getUserById(userId).get();
    }

    @Override
    public Collection<User> getUsersByIds(Collection<Long> ids) {
        return userDao.getUsersByIds((Set<Long>) ids);
    }

    private boolean isExist(long userId) {
        return userDao.getUserById(userId).isPresent();
    }
}
