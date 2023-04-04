package ru.yandex.practicum.filmorate.controller.exception;

public class ValidationException extends RuntimeException {
    public ValidationException (String s) {
        super(s);
    }

    public ValidationException () {
        super();
    }
}
