## Launching the Spring Boot Application

First, you need to run a Maven clean install command. Then it is possible to run the application using Docker, an IDE or even console commands. These options allow you to launch the application in different ways depending on the development environment.

```bash
mvn clean install 
```

### 1. Docker
```bash
docker-compose -f ./docker-compose.yaml up
```

### 2. Console command
```bash
java -jar ./server/target/caradvertiser-server-0.0.1-SNAPSHOT.jar
# or 
mvn spring-boot:run
```



## What I used for the program
- Spring Boot, Lombok, Mapstruct, H2 database, Openapi generator, Spring Security, Liquibase, JUnit, Mockito, Hamcrest

## Test scenarios

### Signup
```bash
curl --location 'http://localhost:8081/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "testUser6",
    "email": "test6@email.com",
    "password": "dummyPassword6"
}'
```
### Login
```bash
curl --location 'http://localhost:8081/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "testUser6",
    "password": "dummyPassword6"
}'
```

### Create Advertisement (Success)

```bash
curl --location 'http://localhost:8081/api/v1/ad' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjYiLCJpYXQiOjE3MTA3NjEzNTIsImV4cCI6MTcxMDc2NDk1Mn0.hc14SD6EtsepL-WXwtw42-5-P8eyliJe66nwHFug2f4' \
--data '{
    "brand": "Honda",
    "type": "Coupe",
    "description": "A sporty coupe designed for enthusiasts seeking performance and style.",
    "price": 30000
}'
```

### Create Advertisement (Fail)
```bash
curl --location 'http://localhost:8081/api/v1/ad' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjYiLCJpYXQiOjE3MTA3NjEzNTIsImV4cCI6MTcxMDc2NDk1Mn0.hc14SD6EtsepL-WXwtw42-5-P8eyliJe66nwHFug2f4' \
--data '{
    "brand": "WayTooLongBrandNameUnfortunatelyItWillFail",
    "type": "Coupe",
    "description": "A sporty coupe designed for enthusiasts seeking performance and style.",
    "price": 30000
}'
```



## Suggestions to make the Application production ready

### 1. Test coverage and vulnerability testing
The quality and stability of code is a critical aspect of the application lifecycle. By achieving at least 85% test coverage, we ensure that the application works as intended. It is also important to regularly test the application for vulnerabilities. For this we can use Trivy or Snyk to identify vulnerabilities and increase the version number of the dependencies.
### 2. Database optimization
The database is another key element for application performance and scalability. For example using PostgreSQL instead of H2 inmemory database, it can improve the data management capabilities and security of the application.
### 3. Keycloak Access Manager
It's worth considering integrating Keycloak Access Manager with Spring Security to effectively manage authentication and authorization, ensuring data security and protection against illegal intrusions.
### 4. CI/CD Configuration and Logging
With the right configuration of a continuous integration and delivery (CI/CD) system, we can speed up the application development cycle, enabling fast and reliable updates. And with proper logging, we can easily identify and manage any bugs, helping to ensure the overall stability and development of the application.
### 5. Performance testing
Measuring and optimising the performance of the application is essential for a good user experience and efficient operation. We can use advanced performance testing to make sure the application is responding well to real-world environmental loads and take steps to improve performance where necessary.