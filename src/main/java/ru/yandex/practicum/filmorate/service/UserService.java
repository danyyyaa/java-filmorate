package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    void addFriend(long userId, long friendId);

    User getUserById(long userId);

    Collection<User> getFriends(long userId);

    void unfriend(long userId, long friendId);

    Collection<User> getCommonFriends(long userId, long otherId);

    User createUser(User user);

    User updateUser(User user);

    Collection<User> getUsers();
}
