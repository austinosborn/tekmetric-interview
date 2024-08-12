-- Create the demo schema
CREATE SCHEMA demo;

CREATE TABLE demo.users
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(64)  NOT NULL UNIQUE,
    first_name  VARCHAR(64)  NOT NULL,
    last_name   VARCHAR(64)  NOT NULL,
    enabled     BOOLEAN      NOT NULL DEFAULT TRUE,
    password    VARCHAR(100) NOT NULL,
    external_id VARCHAR(36)  NOT NULL
);

CREATE TABLE demo.roles
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE demo.user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE demo.products
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(128),
    description        VARCHAR(1024),
    price              DECIMAL(19, 2),
    available_quantity INT,
    external_id        VARCHAR(255) NOT NULL,
    UNIQUE (external_id)
);

CREATE TABLE demo.cart_items
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT         NOT NULL,
    product_id  BIGINT         NOT NULL,
    quantity    INT            NOT NULL,
    total_price DECIMAL(19, 2) NOT NULL,
    external_id VARCHAR(255)   NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);


