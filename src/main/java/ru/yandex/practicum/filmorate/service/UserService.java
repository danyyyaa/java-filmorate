package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService implements UserStorage {
    private final InMemoryUserStorage inMemoryUserStorage;

    public User addFriend(User user) {
        return null;
    }

    public User unfriend(User user) {
        return null;
    }

    public Collection<User> getMutualFriend() {
        return null;
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
