# EmployeeDataStorage
##Technologies Information

| Technology     | Badge |
|:--------------:|:-----:|
| Travis CI      | [![Build Status](https://travis-ci.org/tigratius/EmployeeDataStorage.svg?branch=master)](https://travis-ci.org/tigratius/EmployeeDataStorage) |
| Codecov        | [![codecov](https://codecov.io/gh/tigratius/EmployeeDataStorage/branch/master/graph/badge.svg)](https://codecov.io/gh/tigratius/EmployeeDataStorage) |

## Project Information

Приложение (REST API), которое позволяет хранить данные о сотрудниках.

Сотрудник должен содержать данные о департаменте, имя, фамилия, ЗП, дата рождения, дата приёма на работу.
Необходимо реализовать разграничение доступа:
- Администратор (ROLE_ADMIN): полный доступ к приложению (управление сотрудниками, департаментами и пользователями)
- Модератор (ROLE_MODERATOR): полное управление сотрудниками и департаментами
- Пользователь (ROLE_USER): только чтение данных о сотрудниках и департаментах

Требования:

- Приложение должно быть развернуто на heroku (https://www.heroku.com/)
- В github репозитории должен отображатся статус сборки (travis CI - https://travis-ci.org/)
- Необходимо реализовать регистрацию и аутентификацию пользователей.
    При регистрации - роль по умолчанию ROLE_USER.
- Необходимо реализовать подтверждение регистрации по номеру телефона (twilio - https://www.twilio.com/)
- Необходимо реализовать два окружения запуска - dev и prod
(application-dev.properties / application-prod.properties)


Технологии: Java, MySQL, Spring (MVC, Web, Data, Security, Boot), Lombok, Maven, Liquibase.

