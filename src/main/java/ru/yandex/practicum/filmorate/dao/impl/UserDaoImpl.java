package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        Map<String, Object> keys = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("user_t")
                .usingColumns("name", "email", "login", "birthday")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKeyHolder(Map.of("login", user.getLogin(),
                        "email", user.getEmail(),
                        "name", user.getName(),
                        "birthday", java.sql.Date.valueOf(user.getBirthday())))
                .getKeys();
        assert keys != null;
        user.setId((Long) keys.get("id"));

        return user;
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update("UPDATE user_t SET id = ?, email = ?, login = ?, name = ?, birthday = ? where id = ? ",
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public Optional<User> getUserById(long id) {
        String sqlToUserTable = "select * from user_t where id = ? ";
        return jdbcTemplate.query(sqlToUserTable, (rs, rowNum) -> mapToUser(rs), id)
                .stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

    private User mapToUser(ResultSet userRows) throws SQLException {
        long userId = userRows.getLong("id");
        if (userId <= 0) {
            return null;
        }
        LocalDate birthday = userRows.getDate("birthday").toLocalDate();
        return new User(
                userRows.getLong("id"),
                userRows.getString("email"),
                userRows.getString("login"),
                userRows.getString("name"),
                birthday);
    }

    @Override
    public List<User> getUsers() {
        String sqlToUserTable = "select * from user_t";
        return jdbcTemplate.query(sqlToUserTable, (rs, rowNum) -> mapToUser(rs))
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getUsersByIds(Set<Long> ids) {
        List<User> users = new ArrayList<>();
        if (CollectionUtils.isEmpty(ids)) {
            return users;
        }
        String inSql = ids.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        String sqlToUserTable = String.format("select * from user_t where id in ( %s )", inSql);
        return jdbcTemplate.query(sqlToUserTable, (rs, rowNum) -> mapToUser(rs))
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
