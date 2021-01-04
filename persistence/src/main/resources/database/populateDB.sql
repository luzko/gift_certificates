DELETE FROM gift_certificate_tag;
DELETE FROM gift_certificate;
DELETE FROM tag;

ALTER TABLE gift_certificate_tag AUTO_INCREMENT = 1;
ALTER TABLE gift_certificate AUTO_INCREMENT = 1;
ALTER TABLE tag AUTO_INCREMENT = 1;

INSERT INTO gift_certificate (gift_certificate_name, description, price, duration, create_date, last_update_date)
VALUES ('spa', 'any spa procedure', 50.55, 10, '2020-01-10 21:00:00', '2020-01-14 22:04:00'),
       ('bowling', 'bowling game for two', 30.55, 14, '2020-02-10 18:16:00', '2020-03-14 16:04:00'),
       ('football', 'football match for friends', 88.55, 7, '2020-03-11 17:00:00', '2020-06-14 22:11:00'),
       ('rent car', 'discount rent car', 24.55, 24, '2020-06-03 21:47:00', '2020-09-14 22:44:00');


INSERT INTO tag (tag_name)
VALUES ('#food'),
       ('#photo'),
       ('#game'),
       ('#spa'),
       ('#sport'),
       ('#car'),
       ('#rent');

INSERT INTO gift_certificate_tag (gift_certificate_id_fk, tag_id_fk)
VALUES (1, 4),
       (2, 2),
       (2, 3),
       (3, 5),
       (4, 6),
       (4, 7);
