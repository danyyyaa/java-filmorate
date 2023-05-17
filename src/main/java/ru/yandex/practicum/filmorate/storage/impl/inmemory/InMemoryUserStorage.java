package ru.yandex.practicum.filmorate.storage.impl.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Repository
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private static long id = 1L;
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        user.setId(id);
        users.put(id++, user);
        log.info("Добавлен пользователь: " + user);

        return user;
    }

    @Override
    public Optional<User> updateUser(User user) {
        if (!isExist(user.getId())) {
            throw new UserNotFoundException();
        }
        users.put(user.getId(), user);
        return Optional.of(user);
    }

    @Override
    public Collection<User> getUsers() {
        log.info("Получение пользователей: " + users.values());
        return users.values();
    }

    @Override
    public Optional<User> getUserById(long userId) {
        if (!isExist(userId)) {
            throw new UserNotFoundException();
        }
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public Collection<User> getUsersByIds(Collection<Long> ids) {
        List<User> result = new ArrayList<>();
        for (long id : ids) {
            if (!isExist(id)) {
                throw new UserNotFoundException();
            }
            result.add(users.get(id));
        }
        return result;
    }

    private boolean isExist(long userId) {
        return users.containsKey(userId);
    }
}
