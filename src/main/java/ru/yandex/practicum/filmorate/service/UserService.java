package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserStorage userStorage;

    @Override
    public void addFriend(long id, long friendId) {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        user.getFriendsId().add(friend.getId());
        friend.getFriendsId().add(user.getId());
    }

    @Override
    public User getUserById(long id) {
        return userStorage.getUserById(id);
    }

    @Override
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    @Override
    public Collection<User> getFriends(long id) {
        User user = userStorage.getUserById(id);
        Set<Long> friendIds = user.getFriendsId();
        return userStorage.getUsersByIds(friendIds);
    }

    @Override
    public void unfriend(long id, long friendId) {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        user.getFriendsId().remove(friend.getId());
        friend.getFriendsId().remove(user.getId());
    }

    @Override
    public Collection<User> getCommonFriends(long id, long otherId) {
        User user1 = userStorage.getUserById(id);
        User user2 = userStorage.getUserById(otherId);
        Set<Long> friend1Ids = user1.getFriendsId();
        Set<Long> friend2Ids = user2.getFriendsId();
        Set<Long> commonFriendIds = findCommonElements(friend1Ids, friend2Ids);
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
