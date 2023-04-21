package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
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
        getUserById(user.getId());
        users.put(user.getId(), user);
        log.info("Данные о пользователе {} обновлены.", user.getName());
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        log.info("Получение пользователей: " + users.values());
        return users.values();
    }

    @Override
    public User getUserById(long id) {
        User user = users.get(id);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException(
                String.format("Пользователь с таким id %s не существует", id));
    }

    @Override
    public Collection<User> getUsersByIds(Collection<Long> ids) {
        List<User> result = new ArrayList<>();
        for (long id : ids) {
            User user = getUserById(id);
            result.add(user);
        }
        return result;
    }
}
