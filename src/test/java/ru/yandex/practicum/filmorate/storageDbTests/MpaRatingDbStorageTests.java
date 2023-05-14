package ru.yandex.practicum.filmorate.storageDbTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaRatingDbStorageTests {

    private final MpaRatingStorage mpaRatingStorage;

    @Test
    public void testFindAllGpaRatings() {
        Optional<Collection<MpaRating>> mpaOptional = Optional.ofNullable(mpaRatingStorage.getMpaRatings());
        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa -> assertThat(mpa).first().hasFieldOrProperty("id"));
    }

    @Test
    public void testGetRatingById() {
        Optional<MpaRating> mpaOptional = Optional.ofNullable(mpaRatingStorage.getMpaRatingById(1));
        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa -> assertThat(mpa).hasFieldOrProperty("id"));
    }
}
