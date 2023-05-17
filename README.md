# java-filmorate


Технологии: Java + Spring Boot + Maven + Lombok + JUnit + RESTful API + JDBC


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

### Схема базы данных
Схема отображает отношения таблиц в базе данных:

+ film_t - данные о фильмах (primary key - film_id, foreign keys - mpa_rating_id)
+ genre_t - названия жанров фильма (primary key - genre_id)
+ film_genre_t - данные о жанрах какого-то фильма (primary key - film_genre_id, foreign keys - film_id, genre_id)
+ mpa_rating_t - определяет возрастное ограничение для фильма (primary key - mpa_rating_id)
+ film_like_t - информация о лайках фильма и кто их поставил (primary key - like_id, foreign keys - user_id, film_id)
+ user_t - данные о пользователях (primary key - user_id, foreign keys - friend_id, like_id)
+ friendship_t - содержит информации о статусе «дружбы» между двумя пользователями (primary key - friendship_id, foreign keys - user_id, friend_id)



   °  status = true — в таблице две записи о дружбе двух пользователей (id1 = id2; id2 = id1)
   
   °  status = false — в таблице одна запись о дружбе двух пользователей(id1 = id2).

![image](https://github.com/danyyyaa/java-filmorate/assets/118910569/e1826803-0974-48ef-b488-0f31ff60ea87)

#### Примеры запросов:




 1. Пользователи
 
 
 создание пользователя
 
 ```sql
 INSERT INTO user_t (name, email, login, birthday)
 VALUES ( ?, ?, ?, ? );
 ```
 
 редактирование пользователя
 
 ```sql
 UPDATE user_t
 SET email = ?,
 login = ?,
 name = ?,
 birthday = ?
 WHERE id = ?
 ```
 
 получение списка всех пользователей
 
 ```sql
 SELECT *
 FROM user_t
 ```
 получение информации о пользователе по его id
 
 ```sql
 SELECT *
 FROM user_t
 WHERE id = ?
 ```
 
 добавление в друзья
 
 ```sql
 INSERT INTO friendship_t (user_id, friend_id, status)
 VALUES (?, ?, ?)
 ```
 
 удаление из друзей
 
 ```sql
 DELETE
 FROM friendship_t
 WHERE user_id = ? AND friend_id = ?
 ```
 
 возвращает список пользователей, являющихся его друзьями
 ```sql
 SELECT ut.*
 FROM friendship_t AS fst
 INNER JOIN user_t AS ut ON ut.id = fst.friend_id
 WHERE fst.user_id = ?
 ```
 
 список друзей, общих с другим пользователем
 
 ```sql
 SELECT ut.*
 FROM user_t AS ut
 INNER JOIN friendship_t AS fst ON ut.id = fst.friend_id
 WHERE ut.id = ?

 INTERSECT

 SELECT ut.*
 FROM user_t as ut
 INNER JOIN friendship_t as fst ON ut.id = fst.friend_id
 WHERE fst.user_id = ?
 ```

 2. Фильмы

 создание фильма
 
 ```sql
 INSERT INTO film_t (name, description, release_date, duration, mpa_rating_id)
 VALUES (?, ?, ?, ?, ?)
 ```
 
 редактирование фильма
 
 ```sql
 UPDATE film_t
 SET name = ?,
 description = ?,
 release_date = ?,
 duration = ?,
 mpa_rating_id = ?
 WHERE id = ?
 ```

 получение списка всех фильмов
 
 ```sql
 SELECT ft.*, mpt.name, COUNT(flt.user_id) AS rate
 FROM film_t AS ft
 LEFT JOIN mpa_rating_t AS mpt ON ft.mpa_rating_id = mpt.id
 LEFT JOIN film_like_t AS flt ON ft.id = flt.film_id
 GROUP BY ft.id
 ORDER BY ft.id
 ```
 
 получение информации о фильме по его id
 
 ```sql
 SELECT ft.*, mpt.name, COUNT(flt.user_id) AS rate
 FROM film_t AS ft
 LEFT JOIN mpa_rating_t AS mpt ON ft.mpa_rating_id = mpt.id
 LEFT JOIN film_like_t AS flt ON ft.id = flt.film_id
 WHERE ft.id = 2
 GROUP BY ft.id
 ```
 
 пользователь ставит лайк фильму
 
  ```sql
 INSERT INTO film_like_t (film_id, user_id)
 VALUES (?, ?)
  ```
  
  пользователь удаляет лайк
  
  ```sql
 DELETE
 FROM film_like_t
 WHERE film_id = ? AND user_id = ?
  ```
 
 возвращает список из первых count фильмов по количеству лайков
 ```sql
SELECT ft.*, mpt.name, COUNT(flt.user_id) AS rate
FROM film_t AS ft
LEFT JOIN mpa_rating_t AS mpt ON ft.mpa_rating_id = mpt.id
LEFT JOIN film_like_t AS flt ON ft.id = flt.film_id
GROUP BY ft.id
ORDER BY rate DESC, ft.id
LIMIT ?
 ```
 
 

