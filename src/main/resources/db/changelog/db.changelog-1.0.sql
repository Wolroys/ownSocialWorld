--liquibase formatted sql

--changeset wolroys:1
create table users(
      id bigserial primary key,
      username varchar(128) unique not null,
      email varchar(128) unique not null,
      password varchar(256) not null,
      role varchar(32) default USER,
      created_at timestamp,
      modified_at timestamp);

--changeset wolroys:2
create table posts(
      id bigserial primary key,
      user_id int references users(id),
      title varchar(128),
      content text,
      created_at timestamp,
      modified_at timestamp
);

--changeset wolroys:3
create table comments(
     id bigserial primary key,
     user_id int references users(id),
     post_id int references posts(id),
     content text,
     created_at timestamp,
     modified_at timestamp
);

--changeset wolroys:4
create table likes(
    id bigserial primary key,
    user_id int references users(id),
    posts_id int references posts(id)
);

