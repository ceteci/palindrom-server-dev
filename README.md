# README

## Overview
The Palindrome Application provides a service to validate and process palindromes. It incorporates modern software design principles to ensure scalability, maintainability, and extensibility. The application handles caching, persistence, and input validation seamlessly.

## Architecture
The application employs the following architectural principles:

- **Event-Driven Architecture**: Synchronizes caching and persistence through events.
- **Decoupled Caching and Persistence**: Ensures independent management of caching and persistence layers.
- **Validation Chain**: Modular and extensible validation rules for input validation.

## Key Design Patterns
- **Event-Driven**:
    - Events like `CacheUpdateEvent` and `PersistenceSaveEvent` decouple the triggering and handling of cache and persistence operations.
- **Factory Pattern**:
    - Dynamically selects the persistence mechanism (e.g., file-based or database-based) based on configuration.
- **Validation Chain**:
    - Uses a chain of responsibility to validate input modularly and extensibly.

## Features
1. **Cache-Persistence Synchronization**:
    - Uses `CacheUpdateEvent` and `PersistenceSaveEvent` for synchronization.
2. **Extensible Validation**:
    - New validation rules can be added dynamically.
3. **Structured Logging**:
    - JSON-style logs enhance observability and integration with tools like ELK.
4. **Resilience4j for Fault Tolerance**:
    - Incorporates retry and fallback mechanisms for transient errors in cache or persistence operations.

## Best Practices
- **Dependency Injection**:
    - Ensures testability and modularity.
- **Separation of Concerns**:
    - Caching, persistence, and validation are handled in separate layers.
- **Observability**:
    - Structured logs provide better traceability.

## Running the Application
### Prerequisites
- Java 17 or later.
- Maven 3.6 or later.
- A database for persistence (optional, based on configuration).

### Steps
1. Clone the repository.
2. Configure the application in `application.properties` or `application-test.properties`.
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Running Tests
1. Execute unit tests:
   ```bash
   mvn test
   ```
2. Run integration tests:
   ```bash
   mvn verify
   ```

## Logs
Sample structured log entry:
```json
{
  "timestamp": "2025-01-30T14:56:00Z",
  "level": "INFO",
  "message": "Cache hit for 'radar'",
  "method": "processRequest",
  "layer": "SERVICE",
  "executionTimeMs": 12
}
```