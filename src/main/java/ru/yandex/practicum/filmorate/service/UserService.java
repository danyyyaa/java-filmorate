package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserStorage {
    private final InMemoryUserStorage inMemoryUserStorage;

    public User addFriend(User user1, User user2) {
        user1.addFriend(user2.getId());
        user2.addFriend(user1.getId());
        return null;
    }

    public User unfriend(User user) {
        user.unfriend(user.getId());
        return user;
    }

    public Collection<User> getMutualFriends(User user1, User user2) {
        /*return user1
                .getFriendsId()
                .stream()
                .filter(user2.getFriendsId()::contains)
                .collect(Collectors.toList());*/
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
