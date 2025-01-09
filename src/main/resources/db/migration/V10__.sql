CREATE TABLE pools (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE pool_users (
                            pool_id BIGINT NOT NULL,
                            user_id BIGINT NOT NULL,
                            PRIMARY KEY (pool_id, user_id),
                            FOREIGN KEY (pool_id) REFERENCES pools(id),
                            FOREIGN KEY (user_id) REFERENCES app_users(id)
);

CREATE SEQUENCE pools_seq INCREMENT BY 50 START WITH 1;


CREATE INDEX idx_pool_users_pool_id ON pool_users(pool_id);
CREATE INDEX idx_pool_users_user_id ON pool_users(user_id);

