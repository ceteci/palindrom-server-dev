## Design Overview
This document explains the architectural and design choices of the Palindrome Application.

### Event-Driven Architecture
The application uses events for:
- **Cache Updates**:
    - `CacheUpdateEvent` triggers updates to the cache.
- **Persistence**:
    - `PersistenceSaveEvent` triggers persistence operations.

### Caching and Persistence
- **Cache**:
    - A decoupled caching mechanism ensures that cache operations are independent.
- **Persistence**:
    - Managed via a factory pattern to dynamically select the persistence strategy (file or database).

### Validation
The `ValidationChain` applies a chain of responsibility to handle input validation. Rules can be dynamically added or modified without impacting the service layer.

### Resilience4j Integration
- **Retry Mechanism**:
    - Configured to handle transient errors in cache or persistence layers.
- **Fallbacks**:
    - Ensures graceful degradation during failures.

### Logging
- **Structured Logging**:
    - Logs are in JSON format for easy parsing and integration with log aggregation tools.
- **Request and Trace IDs**:
    - Each log entry includes IDs for easier debugging.

## Key Classes and Components
### PalindromeService
Handles the core business logic:
- Validates input.
- Checks cache.
- Publishes events for cache and persistence updates.

### Events
- **CacheUpdateEvent**:
    - Signals cache updates.
- **PersistenceSaveEvent**:
    - Signals persistence operations.

### Listeners
Handles events to:
- Update the cache.
- Save data to the persistence layer.

### ValidationChain
- Dynamically manages validation rules.
- Ensures modularity and extensibility.

### Factory Pattern
- **PersistenceFactory**:
    - Chooses the persistence strategy based on configuration.

## Example Flow
1. A request is sent to validate a palindrome.
2. The `PalindromeService`:
    - Validates the input.
    - Checks the cache for a hit.
    - If not found, determines if it’s a palindrome.
    - Publishes events for cache and persistence updates.

## Future Improvements
1. Introduce distributed caching (e.g., Redis).
2. Add support for dynamic cache expiration policies.
3. Enhance validation rules with custom annotations.
4. Expand fault-tolerance mechanisms using Resilience4j.