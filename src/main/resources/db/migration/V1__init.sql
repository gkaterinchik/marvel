create table characters (
                       id                    bigserial,
                       name                  varchar(20) not null ,
                       description           varchar(300) not null,
                       thumbnail_uri         varchar(100),
                       primary key (id)
);


