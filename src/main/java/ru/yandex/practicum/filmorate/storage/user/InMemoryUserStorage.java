package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private static long id = 1L;
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
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
            log.error("Обновление несуществующего пользователя");
            throw new UserNotFoundException("Обновление несуществующего пользователя");
        }

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        //user.setFriendsId(new HashSet<>());
        users.put(user.getId(), user);
        log.info("Обновлен пользователь: " + user);

        return user;
    }

    @Override
    public Collection<User> getUsers() {
        log.info("Получение пользователей");
        return users.values();
    }

    @Override
    public User getUserById(long id) {
        if (!users.containsKey(id)) {
            log.error("Получение несуществующего пользователя: " + id);
            throw new UserNotFoundException("Ошибка, пользователь " + id + " не найден.");
        }
        return users.get(id);
    }
}
