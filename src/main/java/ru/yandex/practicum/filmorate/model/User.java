package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.ValidationException;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//@Builder
@Getter
@Setter
@ToString
public class User {
    @PositiveOrZero
    private long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    private Set<Long> friendsId = new HashSet<>();

    public void addFriend(long id) {
        if (id < 1) {
            throw new ValidationException();
        }
        friendsId.add(id);
    }

    public void unfriend(long id) {
        friendsId.remove(id);
    }
}
