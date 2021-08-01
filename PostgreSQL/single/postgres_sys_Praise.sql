CREATE TABLE sys."Praise"
(
    id bigint PRIMARY KEY NOT NULL,
    "momentId" bigint NOT NULL,
    "userId" bigint NOT NULL,
    date timestamp(6)
);
COMMENT ON COLUMN sys."Praise".id IS '动态id';
COMMENT ON COLUMN sys."Praise"."momentId" IS '唯一标识';
COMMENT ON COLUMN sys."Praise"."userId" IS '用户id';
COMMENT ON COLUMN sys."Praise".date IS '点赞时间';
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (1, 12, 82001, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (2, 15, 82002, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (3, 32, 82003, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (4, 58, 82004, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (5, 170, 82005, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (6, 235, 82006, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (7, 301, 82007, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (8, 371, 82008, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (9, 470, 82009, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (10, 511, 82010, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (11, 543, 82011, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (12, 551, 82012, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (13, 594, 82013, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (14, 595, 82014, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (15, 704, 82015, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (16, 1491200468898, 82016, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (17, 1491277116776, 82017, '2017-11-19 13:02:30.000000');
INSERT INTO sys."Praise" (id, "momentId", "userId", date) VALUES (18, 1493835799335, 82018, '2017-11-19 13:02:30.000000');