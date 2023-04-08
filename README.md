# Weatherstack Open Service
[Link: weatherstack.com](https://weatherstack.com)

This service provides current weather report for last 30 minutes based on city names.

Limit request amount(free subscription indicates 250 requests/month) by using IP of client and rate limiting feature of [resilience4J](https://resilience4j.readme.io/docs).
For fast response from in-memory caching is supplied by [Spring Caching Mechanism](https://spring.io/guides/gs/caching/).

Dockerized/containerized by the help [Docker](http://docker.com) and deployed as 3 replicas by using Docker-compose.

[OpenApi](https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md) - Swagger UI is used for documentation.

## Some used technologies in this project:

### Language and framework
- Java 19
- Kotlin
- Spring Boot 3.0
- Spring Data JPA
- Maven

### ORM and DB
- Hibernate
- H2 In Memory Database

### Virtualization
- Docker & Docker Compose

### Monitoring
- Prometheus
- Grafana
