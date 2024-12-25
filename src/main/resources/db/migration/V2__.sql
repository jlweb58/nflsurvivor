CREATE TABLE users_roles
(
    user_id   BIGINT       NOT NULL,
    user_role VARCHAR(255) NOT NULL,
    CONSTRAINT PK_USER_USER_ROLE PRIMARY KEY (user_id, user_role)
);

ALTER TABLE users_roles
   ADD CONSTRAINT FK_USERS_ON_USERS_ROLES FOREIGN KEY (user_id) REFERENCES app_users (id);
