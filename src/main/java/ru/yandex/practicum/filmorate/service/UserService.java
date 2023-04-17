package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserStorage {
    //private final UserStorage inMemoryUserStorage = new InMemoryUserStorage();
    private final InMemoryUserStorage inMemoryUserStorage;

    public void addFriend(int id, int friendId) {
        inMemoryUserStorage.getMap().get(id).addFriend(friendId);
        inMemoryUserStorage.getMap().get(friendId).addFriend(id);
    }

    public User getUserById(int id) {
        if (!inMemoryUserStorage.getMap().containsKey(id)) {
            log.error("Ошибка, пользователь " + id + " не найден.");
            throw new UserNotFoundException("User not found.");
        }
        log.info("Получен пользователь: " + inMemoryUserStorage.getMap().get(id));
        return inMemoryUserStorage.getMap().get(id);
    }

    public Collection<User> getFriends(int id) {
        return inMemoryUserStorage
                .getMap()
                .get(id)
                .getFriendsId()
                .stream()
                .map(inMemoryUserStorage.getMap()::get)
                .collect(Collectors.toSet());
    }

    public void unfriend(int id, int friendId) {
        inMemoryUserStorage.getMap().get(id).unfriend(friendId);
        inMemoryUserStorage.getMap().get(friendId).unfriend(id);
    }

    public Collection<User> getMutualFriends(int id, int otherId) {
        return inMemoryUserStorage.
                getMap()
                .get(id)
                .getFriendsId()
                .stream()
                .filter(inMemoryUserStorage.getMap().get(otherId).getFriendsId()::contains)
                .map(inMemoryUserStorage.getMap()::get)
                .collect(Collectors.toSet());
    }

    @Override
    public User addUser(User user) {
        return inMemoryUserStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    @Override
    public Collection<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }
}
