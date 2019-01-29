DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);
