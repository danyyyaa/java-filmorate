package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendshipService {

    Collection<Long> getFriendIdsByUserId(long userId);

    Friendship addFriend(long userId, long friendId);

    void unfriend(long userId, long friendId);

    Collection<User> getCommonFriends(long userId1, long userId2);

    Collection<User> getFriendsByUserId(long userId);
}
