# Simple REST API

This project is a simple REST API that features:
- adding customers
- removing customers
- modifying customers

## API endpoints
View all API documentation [here](API_DOC.md).

---

## Running in dev environment

### Create PostgreSQL database

1. Start up PostgreSQL utlity.
    - `psql -U {username} -P`
2. Create necessary database.
    - Database name is defined `application.yml` -> `spring.datasource.url`
        - Also make sure PSQL username and password in `application.yml` are correct.
    - Run `CREATE DATABASE {databaseName}`.
    - Confirm database has been created by running `\l`

### Enable annotation processing for Lombok

For IntelliJ IDEA, setup is following:

1. Settings -> Annotation Processors
2. Check **Enable annotation processing**.

### Running

`./gradlew bootRun`

## Tools used

* [Spring Boot](https://spring.io/projects/spring-boot) - Framework
* [Gradle](https://gradle.org) - Dependency management
* [Project Lombok](https://projectlombok.org) - Reducing code
* [PostgreSQL](https://www.postgresql.org) - Database