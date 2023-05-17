package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public User getUserById(long userId) {
        if (!isExist(userId)) {
            log.error("Ошибка, пользователь не существует: " + userId);
            throw new UserNotFoundException();
        }

        User user = userStorage.getUserById(userId).orElseThrow(UserNotFoundException::new);
        log.info("Получен пользователь: " + user);
        return user;
    }

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        User newUser = userStorage.createUser(user);
        log.info("Создан пользователь: " + newUser);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!isExist(user.getId())) {
            log.error("Ошибка, пользователь не существует: " + user);
            throw new UserNotFoundException();
        }
        userStorage.updateUser(user);
        log.info("Обновлен пользователь: " + user);
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        Collection<User> users = userStorage.getUsers();
        log.info("Получены пользователи: " + users);
        return users;
    }

    private boolean isExist(long userId) {
        return userStorage.getUserById(userId).isPresent();
    }

    @Override
    public Collection<User> getUsersByIds(Collection<Long> ids) {
        return userStorage.getUsersByIds(ids);
    }
}
