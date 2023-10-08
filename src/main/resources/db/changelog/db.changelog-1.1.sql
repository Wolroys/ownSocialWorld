create table user_subscription(
    author_id int not null references users(id),
    follower_id int not null references users(id),
    primary key (author_id, follower_id)
);