-- liquibase formatted sql
-- changeset ronlievens:data-1.0

-- ron.lievens@gmail.com
INSERT INTO gebruiker_rollen (user_id, authority)
VALUES (1, 'VERIFIED');
INSERT INTO gebruiker_rollen (user_id, authority)
VALUES (1, 'ADMINISTRATOR');

-- h.casteren@local
INSERT INTO gebruiker_rollen (user_id, authority)
VALUES (2, 'VERIFIED');

-- arie-kaast@local
INSERT INTO gebruiker_rollen (user_id, authority)
VALUES (3, 'VERIFIED');

-- piet.g@local
INSERT INTO gebruiker_rollen (user_id, authority)
VALUES (4, 'VERIFIED');
INSERT INTO gebruiker_rollen (user_id, authority)
VALUES (4, 'TEACHER');
