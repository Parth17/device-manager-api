

# Device Manager API

This is a REST API for managing devices, built with Spring Boot, Java 21, and Maven 3.9. It uses PostgreSQL for persistence.

## Requirements
- Java 21
- Maven 3.9
- PostgreSQL (create a database named `device_db` with user `postgres` and password `postgres`, or update `application.properties`)
- Docker (for containerization)

## Setup
1. Clone the repo: `git clone <repo-url>`
2. Navigate to the project: `cd device-manager-api`
3. Build: `mvn clean install`
4. Run: `mvn spring-boot:run` (ensure PostgreSQL is running)

## API Documentation
Access Swagger UI at `http://localhost:8080/swagger-ui.html` after running the app.

# Device Manager Api

## Endpoints
- POST /api/devices - Create device
- PUT /api/devices/{id} - Full update
- PATCH /api/devices/{id} - Partial update
- GET /api/devices/{id} - Get by ID
- GET /api/devices - Get all
- GET /api/devices/brand/{brand} - Get by brand
- GET /api/devices/state/{state} - Get by state (e.g., AVAILABLE)
- DELETE /api/devices/{id} - Delete

## Validations
- Creation time immutable
- No name/brand updates for in-use devices
- No deletion for in-use devices

## Containerization
Build Docker image: `docker build -t device-manager-api .`
Run: `docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://172.17.0.0:5432/device_db device-manager-api`

## Tests
Run: `mvn test` (covers service and controller layers)

## Notes and Improvement 
- States: AVAILABLE, IN_USE, INACTIVE
- For production, secure DB credentials need to implement 
- Spring Security can also be added for Authorization and Authorization
