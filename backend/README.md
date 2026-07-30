# Biblioteca - Backend

Library Management System API.

## Stack

- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- MySQL
- Maven

## Prerequisites

- JDK 17
- Maven 3.8+
- MySQL 8+

## Configuration

Edit `src/main/resources/application.properties` with your MySQL credentials.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca
spring.datasource.username=root
spring.datasource.password=your_password
```

Create the database:

```sql
CREATE DATABASE biblioteca;
```

## Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.
