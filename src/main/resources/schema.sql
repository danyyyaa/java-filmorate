CREATE TABLE IF NOT EXISTS MPA_RATING_T(
                                           ID BIGINT NOT NULL AUTO_INCREMENT,
                                           NAME VARCHAR NOT NULL,
                                           CONSTRAINT MPA_RATING_PK PRIMARY KEY (ID)
    );
CREATE TABLE IF NOT EXISTS FILM_T(
                                     ID BIGINT NOT NULL AUTO_INCREMENT,
                                     NAME VARCHAR NOT NULL,
                                     DESCRIPTION VARCHAR(200),
    RELEASE_DATE DATE,
    DURATION TIME,
    MPA_RATING_ID BIGINT NOT NULL,
    CONSTRAINT FILM_PK PRIMARY KEY (ID),
    CONSTRAINT FILM_FK FOREIGN KEY (MPA_RATING_ID) REFERENCES MPA_RATING_T(ID) ON DELETE RESTRICT ON UPDATE CASCADE
    );
CREATE TABLE IF NOT EXISTS GENRE_T (
                                       ID BIGINT NOT NULL AUTO_INCREMENT,
                                       NAME VARCHAR NOT NULL,
                                       CONSTRAINT GENRE_PK PRIMARY KEY (ID)
    );
CREATE TABLE IF NOT EXISTS USER_T (
                                      ID BIGINT NOT NULL AUTO_INCREMENT,
                                      NAME VARCHAR,
                                      EMAIL VARCHAR NOT NULL,
                                      LOGIN VARCHAR NOT NULL,
                                      BIRTHDAY DATE,
                                      CONSTRAINT USER_PK PRIMARY KEY (ID)
    );
CREATE TABLE IF NOT EXISTS FILM_GENRE_T (
                                            ID BIGINT NOT NULL AUTO_INCREMENT,
                                            FILM_ID BIGINT NOT NULL,
                                            GENRE_ID BIGINT NOT NULL,
                                            CONSTRAINT FILM_GENRE_PK PRIMARY KEY (ID),
    CONSTRAINT FILM_GENRE_FK FOREIGN KEY (GENRE_ID) REFERENCES GENRE_T(ID) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT FILM_GENRE_FK_1 FOREIGN KEY (FILM_ID) REFERENCES FILM_T(ID) ON DELETE CASCADE ON UPDATE CASCADE
    );
CREATE TABLE IF NOT EXISTS FRIENDSHIP_T (
                                            ID BIGINT NOT NULL AUTO_INCREMENT,
                                            USER_ID BIGINT NOT NULL,
                                            FRIEND_ID BIGINT NOT NULL,
                                            STATUS BOOLEAN NOT NULL,
                                            CONSTRAINT FRIENDSHIP_PK PRIMARY KEY (ID),
    CONSTRAINT FRIENDSHIP_ID_FK FOREIGN KEY (FRIEND_ID) REFERENCES USER_T(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES USER_T(ID) ON DELETE CASCADE ON UPDATE CASCADE
    );
CREATE TABLE IF NOT EXISTS FILM_LIKE_T (
                                           ID BIGINT NOT NULL AUTO_INCREMENT,
                                           FILM_ID BIGINT NOT NULL,
                                           USER_ID BIGINT NOT NULL,
                                           CONSTRAINT ID_PK PRIMARY KEY (ID),
    CONSTRAINT FILM_ID_FK FOREIGN KEY (FILM_ID) REFERENCES FILM_T(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT USER_ID_LKS_FK FOREIGN KEY (USER_ID) REFERENCES USER_T(ID) ON DELETE CASCADE ON UPDATE CASCADE
    );
