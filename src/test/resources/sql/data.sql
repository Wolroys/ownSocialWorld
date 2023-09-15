INSERT INTO users (username, email, password, role)
VALUES
    ('user1', 'user1@example.com', 'password1', 'ADMIN'),
    ('user2', 'user2@example.com', 'password2', 'USER'),
    ('admin', 'admin@example.com', 'adminpassword', 'USER');

INSERT INTO posts (user_id, title, content)
VALUES
    (1, 'Заголовок поста 1', 'Содержание поста 1'),
    (1, 'Заголовок поста 2', 'Содержание поста 2'),
    (2, 'Заголовок поста 3', 'Содержание поста 3');

INSERT INTO comments (user_id, post_id, content)
VALUES
    (1, 1, 'Комментарий к посту 1'),
    (2, 1, 'Комментарий к посту 1'),
    (2, 2, 'Комментарий к посту 2');
