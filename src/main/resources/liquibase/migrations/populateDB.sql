--liquibase formatted sql
--changeset tigratius:2
-- Insert data

-- populate users
INSERT INTO users (username, password, phone_number, created, updated, status) VALUES ('admin', '$2a$10$Bw3Lf8x3cyBw3a9WtKOI3uYCpevyqUOkfuzJty4sVEFGxnrDRTrDK', '+711111111', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), "ACTIVE");

-- populate roles
INSERT INTO roles VALUES (1, 'ROLE_USER', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), "ACTIVE");
INSERT INTO roles VALUES (2, 'ROLE_MODERATOR', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), "ACTIVE");
INSERT INTO roles VALUES (3, 'ROLE_ADMIN', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), "ACTIVE");

-- populate user_roles
INSERT INTO user_roles VALUES (1, 1);
INSERT INTO user_roles VALUES (1, 2);
INSERT INTO user_roles VALUES (1, 3);

-- populate employee
INSERT INTO employees VALUES (1, 'Alex', 'Andreev', 100000, '1986-04-11', '2019-11-11', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), "ACTIVE");
INSERT INTO employees VALUES (2, 'Vlad', 'Andreev', 150000, '1963-05-09', '2018-10-11', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), "ACTIVE");

-- populate departments
INSERT INTO departments VALUES (1, 'Russia Programmers', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), "ACTIVE");
INSERT INTO departments VALUES (2, 'USA Programmers', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), "ACTIVE");

-- populate employee_departments
INSERT INTO employee_departments VALUES (1, 1);
INSERT INTO employee_departments VALUES (2, 1);
INSERT INTO employee_departments VALUES (2, 2);


