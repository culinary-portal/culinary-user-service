# Culinary User Service

## Overview

The Culinary User Service is a Spring Boot application designed to manage users, recipes, ingredients, and related entities for a culinary application. It provides functionalities such as user management, recipe creation, ingredient management, and more.

## Prerequisites

- Java 17 or higher

- Gradle 7.0 or higher

- PostgreSQL or H2 Database

## Getting Started

### Clone the Repository

```sh

git clone https://github.com/yourusername/culinary-user-service.git

cd culinary-user-service

```

### Database Setup

#### PostgreSQL & Redis

Run docker compose to create redis and postgres containers

```sh
docker-compose up
```


#### H2 Database (for testing)

The application is pre-configured to use an H2 in-memory database for testing purposes. No additional setup is required.

### Build and Run the Application

1. Build the application:

```sh

./gradlew build

```

2. Run the application:

Local profile:
```sh

./gradlew bootRun --args='--spring.profiles.active=local'

```

Dev profile:

Prerequisites: run docker compose to create redis and postgres containers
```sh
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Accessing the Application

Once the application is running, you can access it at `http://localhost:8080`.

### API Endpoints

Under swagger endpoint `/swagger-ui/index.html#/`

## Testing

To run the tests, use the following command:

```sh

./gradlew test

```

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.