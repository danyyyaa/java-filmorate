package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
public class User {
    @PositiveOrZero
    private int id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    @PositiveOrZero
    Set<Long> friendsId;

    public void addFriend(long id) {
        friendsId.add(id);
    }

    public void unfriend(long id) {
        friendsId.remove(id);
    }
}
