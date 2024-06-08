CREATE TABLE IF NOT EXISTS cart_products
(
    quantity INTEGER,
    cart_product_id BIGINT AUTO_INCREMENT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (cart_product_id)
    );

CREATE TABLE IF NOT EXISTS carts
(
    cart_id BIGINT AUTO_INCREMENT NOT NULL,
    PRIMARY KEY (cart_id)
    );

CREATE TABLE IF NOT EXISTS carts_products
(
    cart_cart_id BIGINT NOT NULL,
    products_cart_product_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT FKhlf6ykg255bee1d1p0so9378i FOREIGN KEY (products_cart_product_id) REFERENCES cart_products(cart_product_id),
    CONSTRAINT FK9hfmdik2aam15udyoqdf4819 FOREIGN KEY (cart_cart_id) REFERENCES carts(cart_id)
    );

CREATE TABLE IF NOT EXISTS customers
(
    account_enable BOOLEAN,
    account_expired BOOLEAN,
    account_locked BOOLEAN,
    credentials_expired BOOLEAN,
    cart_id BIGINT UNIQUE,
    customer_id BIGINT AUTO_INCREMENT NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (customer_id),
    CONSTRAINT FKihj385ysmggb5xuqydq8nb33e FOREIGN KEY (cart_id) REFERENCES carts(cart_id)
    );

CREATE TABLE IF NOT EXISTS products
(
    price NUMERIC(38, 2),
    quantity_available INTEGER,
    product_id BIGINT AUTO_INCREMENT NOT NULL,
    title VARCHAR(255),
    PRIMARY KEY (product_id)
    );

CREATE TABLE IF NOT EXISTS roles
(
    customer_id BIGINT NOT NULL,
    role_id BIGINT AUTO_INCREMENT NOT NULL,
    role VARCHAR(255) CHECK (role IN ('ADMIN', 'ANONYMOUS', 'USER')),
    PRIMARY KEY (role_id),
    CONSTRAINT role_customer_fk FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
    );
