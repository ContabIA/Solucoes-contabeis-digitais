CREATE TABLE reset_password_token (
    id BIGINT NOT NULL AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (token),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES usuarios(id)
);