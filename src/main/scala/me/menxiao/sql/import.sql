CREATE TABLE IF NOT EXISTS users
(
    id       bigint auto_increment not null,
    username varchar_ignorecase(50),
    password varchar(128),
    enabled  bool
);
CREATE TABLE IF NOT EXISTS authorities
(
    id        bigint auto_increment  not null,
    username  varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);
INSERT INTO users (id, username, password, enabled)
VALUES (1, 'root', 'root', true),
       (2, 'user', 'user', true);
INSERT INTO authorities (id, username, authority)
VALUES (1, 'root', 'ROLE_user'),
       (2, 'root', 'ROLE_admin'),
       (3, 'user', 'ROLE_user');
