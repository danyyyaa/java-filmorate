package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        if (users.get(user.getId()) == null) {
            log.error("Обновление несуществующего пользователя: " + user);
            throw new UserNotFoundException("Обновление несуществующего пользователя");
        }

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);
        log.info("Обновлен пользователь: " + user);

        return user;
    }

    @Override
    public Collection<User> getUsers() {
        log.info("Получение пользователей: " + users.values());
        return users.values();
    }

    @Override
    public User getUserById(long id) {
        if (!users.containsKey(id)) {
            log.error("Получение несуществующего пользователя: " + id);
            throw new UserNotFoundException();
        }
        return users.get(id);
    }
}
