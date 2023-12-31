create table characters
(
    id            bigserial ,
    name          varchar(50)  not null unique ,
    description   varchar(300) not null,
    thumbnail_uri varchar(100),
    primary key (id)
);
create table comics
(
    id          bigserial ,
    title       varchar(50)  not null,
    description varchar(300) not null,
    cover_uri   varchar(100),
    creator     varchar(30),
    primary key (id)

);
insert into characters(name, description, thumbnail_uri)
values ('Archangel','Feared and hated by humans because they''re different, the X-Men are heroic mutants, individuals born with special powers who''ve sworn to use their gifts to protect mutants as well as humans.',
        'qwerty.lpg'),
    ('X-Xorn (Kuan-Yin Xorn)','Xorn, whose mutation manifested as a tiny, powerful star inside his head, was brainwashed into believing he was the reincarnation of Magneto.,',
        'asdfg.jpg'),
       ('Abomination (Ultimate)','',
        'qwerty.lpg'),
       ('Abyss','Originally a partner of the mind-altering assassin Black Swan, Nijo spied on Deadpool as part of the Swan''s plan to exact revenge for Deadpool falsely taking credit for the Swan''s assassination of the Four Winds crime family, which included Nijo''s brother',
        'qwerty.lpg'),
       ('Y-Man','fhgdhfdfhdhdfhd dsfhdhdfhd dfhdfhdf fdhdfhh hsh ',
        'qwerty.lpg');

insert into comics(title,description,creator,cover_uri)
values ('qwe','asdfghjk','Bob Marley','qwer.jpg'),
       ('asd','zxcvbnm,kjhgffdsaqwrtty','John Wick','123445.png'),
    ('shgdfhf','sadtfhdtujt ererytsw rg er erertrewtr we', 'John Wick','empty'),
    ('hkhgjkljh','steth rtwetreq etweewwtqw erqqwt','Ronnie Coleman','empty');

CREATE TABLE characters_comics
(

    character_id INTEGER NOT NULL REFERENCES characters,
    comics_id   INTEGER NOT NULL REFERENCES comics,
    UNIQUE (character_id, comics_id)
);
insert into characters_comics(character_id, comics_id)
values
    (1,1),
    (1,2),
    (2,1),
    (3,2),
    (3,1),
    (4,4),
    (1,3);
