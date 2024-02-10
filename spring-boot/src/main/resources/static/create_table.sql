CREATE TABLE  IF NOT EXISTS  customers(
    id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    lastName VARCHAR(200) NOT NULL,
    age INT NOT NULL,
    email VARCHAR(200) NOT NULL
);

CREATE TABLE  IF NOT EXISTS  addresses(
    id BIGINT NOT NULL,
    customerId INT NOT NULL,
    addressString VARCHAR(200) NOT NULL
);
