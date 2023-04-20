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
public class UserService implements UserServiceInterface {
    private final UserStorage userStorage = new InMemoryUserStorage();

    @Override
    public void addFriend(long id, long friendId) {
        if (id < 1 || friendId < 1) {
            throw new UserNotFoundException();
        }
        userStorage.getUserById(id).addFriend(friendId);
        userStorage.getUserById(friendId).addFriend(id);
    }

    @Override
    public User getUserById(long id) {
        return userStorage.getUserById(id);
    }

    @Override
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    @Override
    public Collection<User> getFriends(long id) {
        return userStorage
                .getUserById(id)
                .getFriendsId()
                .stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toSet());
    }

    @Override
    public void unfriend(long id, long friendId) {
        userStorage.getUserById(id).unfriend(friendId);
        userStorage.getUserById(friendId).unfriend(id);
    }

    @Override
    public Collection<User> getCommonFriends(long id, long otherId) {
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
