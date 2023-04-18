-- liquibase formatted sql
-- changeset ronlievens:schema-1.0
CREATE TABLE gebruiker_rollen
(
    user_id   BIGINT,
    authority VARCHAR(255),
    CONSTRAINT gebruiker_rollen_pk PRIMARY KEY (user_id, authority),
    CONSTRAINT gebruiker_rollen_fk_1 FOREIGN KEY (user_id) REFERENCES gebruikers (id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE INDEX gebruiker_rollen_i_1 ON gebruiker_rollen (user_id);
