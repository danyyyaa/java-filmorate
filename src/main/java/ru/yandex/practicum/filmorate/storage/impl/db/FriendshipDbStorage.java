package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.util.Collection;

@Repository
@Primary
@RequiredArgsConstructor
public class FriendshipDbStorage implements FriendshipStorage {
    private final FriendshipDao friendshipDao;

    @Override
    public Friendship createFriendship(Friendship friendship) {
        return friendshipDao.createFriendship(friendship);
    }

    @Override
    public void deleteFriendshipById(long userId) {
        friendshipDao.deleteFriendshipByUserId(userId);
    }

    @Override
    public Collection<Long> getFriendIdsByUserId(long userId) {
        return friendshipDao.getFriendIdsByUserId(userId);
    }
}
