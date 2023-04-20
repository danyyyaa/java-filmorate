package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserServiceInterface {
    void addFriend(long id, long friendId);

    User getUserById(long id);

    Collection<User> getFriends(long id);

    void unfriend(long id, long friendId);

    Collection<User> getCommonFriends(long id, long otherId);

    User createUser(User user);

    User updateUser(User user);

    Collection<User> getUsers();
}
