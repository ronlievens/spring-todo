-- liquibase formatted sql
-- changeset ronlievens:schema-1.0
CREATE TABLE gebruiker_tokens
(
    id          BIGINT                  NOT NULL AUTO_INCREMENT,
    user_id     BIGINT                  NOT NULL,
    type        VARCHAR(255)            NOT NULL,
    value       TEXT                    NOT NULL,
    valid_until TIMESTAMP               NOT NULL,
    created_on  TIMESTAMP DEFAULT NOW() NOT NULL,
    disabled_on TIMESTAMP,
    CONSTRAINT gebruiker_tokens_pk PRIMARY KEY (id),
    CONSTRAINT gebruiker_tokens_value_i UNIQUE (value)
);

ALTER TABLE gebruiker_tokens
    ADD CONSTRAINT gebruiker_tokens_fk_1 FOREIGN KEY (user_id) REFERENCES gebruikers (id) ON UPDATE RESTRICT ON DELETE CASCADE;

CREATE INDEX gebruiker_tokens_i_1 ON gebruiker_tokens (user_id);
