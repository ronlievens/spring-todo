-- liquibase formatted sql
-- changeset ronlievens:data-1.0

-- password= Ikweethetwachtwoordnietmeer!
INSERT INTO gebruikers (id, first_name, last_name, language_code, email, pass_hash, date_of_birth, gender, created_on)
VALUES (1, 'Ron', 'Lievens', 'NL', 'ron.lievens@gmail.com',
        '$2y$12$nbzHwYi61RCHTPuFTTkNtOSo1Vy9v9MvAPvJpJ6B/y5qv38PjfxcG', '1978-08-15 00:00:00', 'MALE',
        '2010-01-01 00:00:00');

-- password= justateststringfornow
INSERT INTO gebruikers (id, first_name, last_name, language_code, email, pass_hash, date_of_birth, gender, created_on)
VALUES (2, 'Henk', 'Casteren', 'NL', 'h.casteren@local',
        '$2y$12$1E9Ba8fLd/slAA9Wr6KsieHJVqabkTw3D6EnGh04RvDsbmrpu0ZLG', '1965-03-05 00:00:00', 'MALE',
        '2010-01-01 00:00:00');

-- password= justateststringfornow
INSERT INTO gebruikers (id, first_name, infix, last_name, language_code, email, pass_hash, date_of_birth, gender,
                        created_on)
VALUES (3, 'Arie', 'van den', 'Kaast', 'EN', 'arie-kaast@local',
        '$2y$12$JwK4YAyi6CQFb35WyzdhxONtE5LqN8Aa92o11BeVB6FHm382pDmPG', '1995-09-27 00:00:00', 'MALE',
        '2010-01-01 00:00:00');

-- password= ignorethistoo
INSERT INTO gebruikers (id, first_name, last_name, language_code, email, pass_hash, date_of_birth, gender, created_on)
VALUES (4, 'Piet', 'Gersen', 'DE', 'piet.g@local',
        '$2y$12$9FT2U5MBmhAA5635C5QvXeuWZGiRHNSKvzQeLHHTbbqJwLsLWdgZK', '2006-11-11 00:00:00', 'MALE',
        '2010-01-01 00:00:00');
