package ru.yandex.practicum.filmorate.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendshipService;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipStorage friendshipStorage;
    private final UserStorage userStorage;

    @Override
    public Collection<Long> getFriendIdsByUserId(long userId) {
        Set<Long> friends = (Set<Long>) friendshipStorage.getFriendIdsByUserId(userId);

        return userStorage.getUsersByIds(friends)
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Friendship addFriend(long userId, long friendId) {
        userStorage.getUserById(userId);
        userStorage.getUserById(friendId);
        return friendshipStorage.createFriendship(Friendship.builder()
                .userId(userId)
                .friendId(friendId)
                .build());

    }

    @Override
    public void unfriend(long userId, long friendId) {
        friendshipStorage.deleteFriendship(Friendship.builder()
                .userId(userId)
                .friendId(friendId)
                .build());
    }

    @Override
    public Collection<User> getCommonFriends(long userId1, long userId2) {
        Set<Long> userFriendsId = new HashSet<>(friendshipStorage.getFriendIdsByUserId(userId1));
        Set<Long> friendFriendsId = new HashSet<>(friendshipStorage.getFriendIdsByUserId(userId2));
        Set<Long> commonFriendsIds = new HashSet<>(getCommonElements(userFriendsId, friendFriendsId));
        return userStorage.getUsersByIds(commonFriendsIds);
    }

    @Override
    public Collection<User> getFriendsByUserId(long userId) {
        Set<Long> friends = (Set<Long>) friendshipStorage.getFriendIdsByUserId(userId);

        return userStorage.getUsersByIds(friends);
    }

    private static <T> Set<T> getCommonElements(Set<T> first, Set<T> second) {
        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }
}
