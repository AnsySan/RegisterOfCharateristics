create table news
(
    id               bigserial primary key generated always as identity unique,
    title            varchar(150) not null,
    text             varchar(2000) not null,
    creationDate     timestamptz DEFAULT current_timestamp,
    lastEditDate     timestamptz DEFAULT current_timestamp,
    insertedById     bigint not null,
    updatedById      bigint not null
    );

create table comments
(
    id                  bigserial primary key generated always as identity unique,
    text                varchar(300) not null,
    creationDate        timestamp(6),
    lastEditDate        timestamp(6),
    insertedById        bigint not null,
    idNews              bigint not null
    constraint fk_news_comments foreign key (news_id) references news (id) on delete cascade on update no action
    );


create table users
(
    id               bigserial primary key generated always as identity unique,
    username         varchar(40) not null,
    password         varchar(80) not null,
    name             varchar(20) not null,
    surname          varchar(20) not null,
    parentName       varchar(20) not null,
    creationDate     timestamp(6),
    lastEditDate     timestamp(6),
    );

create table roles
(
    user_id bigint not null,
    roles   varchar(255) not null,
    constraint fk_users_roles foreign key (user_id) references users (id) on delete cascade on update no action
    );