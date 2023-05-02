# java-filmorate


Технологии: Java + Spring Boot + Maven + Lombok + JUnit + RESTful API + PostgreSQL


Данный проект представляет собой бэкенд для сервиса, который работает с фильмами и оценками пользователей и рекомендует фильмы к просмотру.

Основная задача приложения - решить проблему поиска фильмов на вечер. С его помощью вы можете легко найти фильм, который вам понравится. Реализован функционал создания аккаунта, добавления друзей и возможность ставить фильмам лайки. В ленте событий можно увидеть, какие фильмы понравились вашим друзьям. Также есть возможность оставлять отзывы и читать отзывы других пользователей. Поиск поможет найти фильм по ключевым словам. Рекомендации помогут выбрать фильм на основе ваших предпочтений. Функциональность «Популярные фильмы» предлагает вывод самых любимых у зрителей фильмов по жанрам и годам.

### Реализованы следующие эндпоинты:

#### 1. Фильмы
+ POST /films - создание фильма

+ PUT /films - редактирование фильма

+ GET /films - получение списка всех фильмов

+ GET /films/{id} - получение информации о фильме по его id

+ PUT /films/{id}/like/{userId} — поставить лайк фильму

+ DELETE /films/{id}/like/{userId} — удалить лайк фильма

+ GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков. Если значение параметра count не задано, возвращает первые 10.

#### 2. Пользователи

+ POST /users - создание пользователя

+ PUT /users - редактирование пользователя

+ GET /users - получение списка всех пользователей

+ GET /users/{id} - получение данных о пользователе по id

+ PUT /users/{id}/friends/{friendId} — добавление в друзья

+ DELETE /users/{id}/friends/{friendId} — удаление из друзей

+ GET /users/{id}/friends — возвращает список друзей

+ GET /users/{id}/friends/common/{otherId} — возвращает список друзей, общих с другим пользователем

### Валидация
Данные, которые приходят в запросе на добавление нового фильма или пользователя, проходят проверку по следующим критериям:

#### 1. Фильмы
 + название не может быть пустым.
 
 + максимальная длина описания — 200 символов
 
 + дата релиза — не раньше 28 декабря 1895 года
 
 + продолжительность фильма должна быть положительной

#### 2. Пользователи
 + электронная почта не может быть пустой и должна быть электронной почтой (аннотация @Email)
 
 + логин не может быть пустым и содержать пробелы
 
 + имя для отображения может быть пустым — в таком случае будет использован логин
 
 + дата рождения не может быть в будущем.
