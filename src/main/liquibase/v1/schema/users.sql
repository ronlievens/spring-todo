-- liquibase formatted sql
-- changeset ronlievens:schema-1.1
CREATE TABLE gebruikers
(
    id            BIGINT                  NOT NULL AUTO_INCREMENT,
    first_name    VARCHAR(255),
    infix         VARCHAR(255),
    last_name     VARCHAR(255),
    email         VARCHAR(255)            NOT NULL,
    pass_hash     VARCHAR(255),
    language_code VARCHAR(2),
    date_of_birth DATE,
    gender        VARCHAR(8),
    created_on    TIMESTAMP DEFAULT NOW() NOT NULL,
    disabled_on   TIMESTAMP,
    CONSTRAINT gebruikers_pk PRIMARY KEY (id),
    CONSTRAINT gebruikers_email_i UNIQUE (email)
);
