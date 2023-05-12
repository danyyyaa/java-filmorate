package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.model.Friendship;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


import static ru.yandex.practicum.filmorate.constant.FriendshipConstant.*;

@Component
@RequiredArgsConstructor
public class FriendshipDaoImpl implements FriendshipDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Friendship createFriendship(Friendship friendship) {
        boolean friendshipAlreadyExisted = friendshipStatusRevers(friendship, true);
        Map<String, Object> keys = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(FRIENDSHIP_TABLE)
                .usingColumns(USER_ID, FRIEND_ID, STATUS)
                .usingGeneratedKeyColumns(ID)
                .executeAndReturnKeyHolder(Map.of(USER_ID, friendship.getUserId(),
                        FRIEND_ID, friendship.getFriendId(),
                        STATUS, friendshipAlreadyExisted))
                .getKeys();
        assert keys != null;
        friendship.setId((Long) keys.get(ID));
        return friendship;
    }

    @Override
    public Set<Long> getFriendIdsByUserId(long userId) {
        String sqlToFriendshipTable = "select * from friendship_t where user_id = ?";
        return jdbcTemplate.query(sqlToFriendshipTable, (rs, rowNum) -> mapToFriendship(rs), userId)
                .stream()
                .filter(Objects::nonNull)
                .map(Friendship::getFriendId)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Friendship> getFriendship(Friendship friendship) {
        String sqlToFriendshipTable = "select * from friendship_t where user_id = ? and friend_id = ?";

        return jdbcTemplate.query(sqlToFriendshipTable, (rs, rowNum) -> mapToFriendship(rs),
                        friendship.getUserId(),
                        friendship.getFriendId())
                .stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

    @Override
    public void deleteFriendship(Friendship friendship) {
        friendshipStatusRevers(friendship, false);

        String sqlToFriendshipTable = "delete from friendship_t where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sqlToFriendshipTable, friendship.getUserId(),
                friendship.getFriendId());
    }

    private Friendship mapToFriendship(ResultSet userRows) throws SQLException {
        long userId = userRows.getLong(USER_ID);
        long friendId = userRows.getLong(FRIEND_ID);

        if (userId <= 0 || friendId <= 0) {
            return null;
        }

        return new Friendship(
                userRows.getLong(ID),
                userRows.getLong(USER_ID),
                userRows.getLong(FRIEND_ID),
                userRows.getBoolean(STATUS)
        );
    }

    private boolean friendshipStatusRevers(Friendship friendship, boolean status) {
        Optional<Friendship> existedFriendshipOpt = getFriendship(Friendship.builder()
                .userId(friendship.getFriendId())
                .friendId(friendship.getUserId())
                .build());
        if (existedFriendshipOpt.isPresent()) {
            Friendship existedFriendship = existedFriendshipOpt.get();
            existedFriendship.setStatus(status);
            updateFriendship(existedFriendship);
            return true;
        }
        return false;
    }

    private Friendship updateFriendship(Friendship friendship) {
        String sql = "update friendship_t set id = ?, user_id = ?, friend_id = ?, status = ? " +
                " where id = ? ";
        jdbcTemplate.update(sql,
                friendship.getId(),
                friendship.getUserId(),
                friendship.getFriendId(),
                friendship.getStatus(),
                friendship.getId());
        return friendship;
    }
}
