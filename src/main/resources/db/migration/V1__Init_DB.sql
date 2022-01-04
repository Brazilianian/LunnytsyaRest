create sequence hibernate_sequence start 1 increment 1;

create table author
(
    id          int8 not null,
    description varchar(2048) not null,
    image       text,
    primary key (id)
);

create table background_image
(
    id      int8 not null,
    content text not null,
    primary key (id)
);

create table product
(
    id          int8   not null,
    description varchar(2048) not null,
    image       text not null,
    name        varchar(255),
    price       float8 not null,
    is_visible  boolean default true,
    primary key (id)
);
