create sequence "credentials_seq" start with 1 increment by 10;

create table "credentials"
(
    "id"            bigint                   not null,
    "registered_at" timestamp with time zone not null,
    "uid"           uuid                     not null,
    "password"      varchar(255)              not null,
    "email"         varchar(255)             not null,
    primary key ("id"),
    constraint credentials_email_uq unique ("email")
);

create table "roles"
(
    "credentials" bigint       not null,
    "role"        varchar(255) not null check ("role" in ('USER', 'ADMIN'))
);

alter table if exists "roles"
    add constraint "FKbc86cc2ipjvty61s1pp4v2ora"
        foreign key ("credentials")
            references "credentials";
