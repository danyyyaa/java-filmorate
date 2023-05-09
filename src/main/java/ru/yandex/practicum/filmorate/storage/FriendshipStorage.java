package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.Collection;

public interface FriendshipStorage {

    Friendship createFriendship(Friendship friendship);

    void deleteFriendshipById(long userId);

    Collection<Long> getFriendIdsByUserId(long userId);
}
