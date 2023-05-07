package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public void addFriend(long userId, long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriendsId().add(friend.getId());
        friend.getFriendsId().add(user.getId());
    }

    @Override
    public User getUserById(long userId) {
        return userStorage.getUserById(userId);
    }

    @Override
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    @Override
    public Collection<User> getFriends(long userId) {
        User user = userStorage.getUserById(userId);
        Set<Long> friendIds = user.getFriendsId();
        return userStorage.getUsersByIds(friendIds);
    }

    @Override
    public void unfriend(long userId, long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriendsId().remove(friend.getId());
        friend.getFriendsId().remove(user.getId());
    }

    @Override
    public Collection<User> getCommonFriends(long userId, long otherId) {
        User user1 = userStorage.getUserById(userId);
        User user2 = userStorage.getUserById(otherId);
        Set<Long> commonFriendIds = findCommonElements(user1.getFriendsId(), user2.getFriendsId());
        return userStorage.getUsersByIds(commonFriendIds);
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    private static <T> Set<T> findCommonElements(Collection<T> first, Collection<T> second) {
        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }
}
