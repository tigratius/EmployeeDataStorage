--liquibase formatted sql
--changeset tigratius:1

-- Table: users
CREATE TABLE IF NOT EXISTS users
(
    id              BIGINT              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username        VARCHAR(255)        NOT NULL UNIQUE,
    password        VARCHAR(255)        NOT NULL,
    phone_number    VARCHAR(50)         NOT NULL UNIQUE,
    created         TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated         TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    status          VARCHAR(25)         NOT NULL DEFAULT "NOT_ACTIVE"
)
    ENGINE = InnoDB;

-- Table: roles
CREATE TABLE IF NOT EXISTS roles
(
    id          BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    created     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    status      VARCHAR(25)     NOT NULL DEFAULT "ACTIVE"
)
    ENGINE = InnoDB;

-- Table: employees
CREATE TABLE IF NOT EXISTS employees
(
    id              BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    salary          DECIMAL      NOT NULL,
    birth_date      DATETIME     NOT NULL,
    employment_date DATETIME     NOT NULL,
    created         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    status          VARCHAR(25)  NOT NULL DEFAULT "ACTIVE"
)
    ENGINE = InnoDB;

-- Table: departments
CREATE TABLE IF NOT EXISTS departments
(
    id          BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    created     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    status      VARCHAR(25)     NOT NULL DEFAULT "ACTIVE"
)
    ENGINE = InnoDB;

-- Table for mapping User and roles: user_roles
CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    #CONSTRAINT `FK_user_roles` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    #CONSTRAINT `FK_roles_users` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,

    UNIQUE (user_id, role_id)
)
    ENGINE = InnoDB;

-- Table for mapping employee and departments: employee_departments
CREATE TABLE IF NOT EXISTS employee_departments
(
    employee_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,

    #CONSTRAINT `FK_employee_departments` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE,
    #CONSTRAINT `FK_departments_employees` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON DELETE CASCADE

    FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE CASCADE,

    UNIQUE (employee_id, department_id)
)
    ENGINE = InnoDB;

