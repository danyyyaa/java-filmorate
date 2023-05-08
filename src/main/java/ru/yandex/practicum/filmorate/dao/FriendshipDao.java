package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.Collection;
import java.util.Optional;

public interface FriendshipDao {

    Optional<Friendship> getFriendshipById(long id);

    void deleteFriendshipByUserId(long id);

    Collection<Friendship> getFriendships();

    Friendship createFriendship(Friendship friendship);

}
