package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private static long id = 1L;
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(id);
        users.put(id++, user);
        log.info("Добавлен пользователь: " + user);

        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!isExist(user.getId())) {
            throw new UserNotFoundException();
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        log.info("Получение пользователей: " + users.values());
        return users.values();
    }

    @Override
    public User getUserById(long userId) {
        if (!isExist(userId)) {
            throw new UserNotFoundException();
        }
        return users.get(userId);
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
