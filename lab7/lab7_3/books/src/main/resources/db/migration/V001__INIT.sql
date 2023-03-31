CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) not null
);

INSERT INTO book VALUES (10, 'Book1');
INSERT INTO book VALUES (20, 'Book2');
INSERT INTO book VALUES (30, 'Book3');