create sequence "user_seq" start with 1 increment by 10;

create table "users"
(
    "id"            bigint                   not null,
    "registered_at" timestamp with time zone not null,
    "email"         varchar(255)             not null,
    "first_name"    varchar(255)             not null,
    "last_name"     varchar(255)             not null,
    primary key ("id"),
    constraint users_email_uq unique ("email")
);
