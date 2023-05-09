package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FriendshipDaoImpl implements FriendshipDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Friendship> getFriendshipById(long id) {
        return jdbcTemplate.query("SELECT * FROM friendship_t WHERE id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Friendship.class))
                .stream()
                .findAny();
    }

    @Override
    public void deleteFriendshipByUserId(long id) {
        jdbcTemplate.update("DELETE FROM friendship_t WHERE id = ?", id);
    }

    @Override
    public Collection<Friendship> getFriendships() {
        return jdbcTemplate.query("SELECT * FROM friendship_t", new BeanPropertyRowMapper<>(Friendship.class));
    }

    @Override
    public Friendship createFriendship(Friendship friendship) {
        jdbcTemplate.update("INSERT INTO friendship_t VALUES(?, ?, ?, ?)",
                friendship.getId(),
                friendship.getUserId(),
                friendship.getFriendId(),
                friendship.getStatus());
        return friendship;
    }

    @Override
    public Collection<Long> getFriendIdsByUserId(long userId) {
        return jdbcTemplate.query("SELECT user_id FROM friendship_t WHERE id = ?",
                        new Object[]{userId}, new BeanPropertyRowMapper<>(Friendship.class))
                .stream()
                .map(Friendship::getFriendId)
                .collect(Collectors.toSet());
    }
}
