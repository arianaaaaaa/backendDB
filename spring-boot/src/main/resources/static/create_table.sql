CREATE TABLE IF NOT EXISTS customers
(
    id        BIGINT       NOT NULL,
    name      VARCHAR(100) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    age       INT          NOT NULL,
    email     VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS addresses
(
    id          BIGINT      NOT NULL,
    customer_id INT         NOT NULL,
    street_name varchar(50) NOT NULL,
    number      INT         NOT NULL,
    letter      varchar(2),
    toevoeging  INT,
    post_code   varchar(7)  NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (ID)
);
