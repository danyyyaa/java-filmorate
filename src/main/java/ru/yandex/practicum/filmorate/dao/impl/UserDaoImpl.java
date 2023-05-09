package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    /*@Override
    public User createUser(User user) {
        jdbcTemplate.update("INSERT INTO user_t VALUES(?, ?, ?, ?, ?)",
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        return user;
    }*/

    @Override
    public User createUser(User user) {
        jdbcTemplate.update("INSERT INTO user_t VALUES(email, login, name, birthday)",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        return user;
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update("UPDATE user_t SET id = ?, email = ?, login = ?, name = ?, birthday = ?",
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        return user;
    }

    @Override
    public Optional<User> getUserById(long userId) {
        return jdbcTemplate.query("SELECT * FROM user_t WHERE id = ?",
                        new Object[]{userId},
                        new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findAny();
    }

    @Override
    public Collection<User> getUsers() {
        return jdbcTemplate.query("SELECT * FROM user_t", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Collection<User> getUsersByIds(Set<Long> ids) {
        return jdbcTemplate.query("SELECT * FROM user_t WHERE id = ?",
                new Object[]{ids},
                new BeanPropertyRowMapper<>(User.class));
    }
}
