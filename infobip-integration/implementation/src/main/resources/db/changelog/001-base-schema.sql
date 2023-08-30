create sequence "message_seq" start with 1 increment by 10;

create table "message"
(
    "id"         bigint                      not null,
    "sent_at"    timestamp(6) with time zone not null,
    "bulk_id"    varchar(255)                not null,
    "message_id" varchar(255)                not null,
    "content"    varchar(255)                not null,
    "email"      varchar(255)                not null,
    "subject"    varchar(255)                not null,
    primary key ("id"),
    constraint uq_message_email unique ("email")
);
