package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserStorage userStorage = new InMemoryUserStorage();

    @Override
    public void addFriend(long id, long friendId) {
        log.info("Пользователь " + id + " добавляет в друзья пользователя " + friendId);
        userStorage.getUserById(id).addFriend(friendId);
        userStorage.getUserById(friendId).addFriend(id);
    }

    @Override
    public User getUserById(long id) {
        if (userStorage.getUserById(id) == null) {
            log.error("Ошибка, пользователь " + id + " не найден.");
            throw new UserNotFoundException("User not found.");
        }
        log.info("Получен пользователь: " + userStorage.getUserById(id));
        return userStorage.getUserById(id);
    }

    @Override
    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    @Override
    public Collection<User> getFriends(long id) {
        log.info("Получение друзей пользователя " + id);
        log.info("\n" + userStorage
                .getUserById(id)
                .getFriendsId()
                .stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toSet()));

        return userStorage
                .getUserById(id)
                .getFriendsId()
                .stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toSet());
    }

    @Override
    public void unfriend(long id, long friendId) {
        if (id < 1 || friendId < 1) {
            throw new ValidationException();
        }
        log.info("Удаление из друзей пользователя " + id + " и " + friendId);
        userStorage.getUserById(id).unfriend(friendId);
        userStorage.getUserById(friendId).unfriend(id);
    }

    @Override
    public Collection<User> getCommonFriends(long id, long otherId) {
        log.info("Получение общих друзей пользователя " + id + " и " + otherId);
        log.info("\n" + userStorage
                .getUserById(id)
                .getFriendsId()
                .stream()
                .filter(userStorage.getUserById(otherId).getFriendsId()::contains)
                .map(userStorage::getUserById)
                .collect(Collectors.toSet()));

        return userStorage
                .getUserById(id)
                .getFriendsId()
                .stream()
                .filter(userStorage.getUserById(otherId).getFriendsId()::contains)
                .map(userStorage::getUserById)
                .collect(Collectors.toSet());
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }
}
