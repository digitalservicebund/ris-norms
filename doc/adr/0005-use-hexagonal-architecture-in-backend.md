# 4. Use hexagonal architecture in Backend

Date: 2024-01-29

## Status

Accepted

## Context

The NeuRIS project aims for a cohesive and standardized architecture across all its services. Several teams within the
project have successfully implemented the Hexagonal (Ports and Adapters) architecture in their respective services,
leading to improved modularity, testability, and overall code maintainability. See [ADR Nr. 7 Ports and Adapters architecture](https://github.com/digitalservicebund/ris-backend-service/blob/main/doc/adr/0007-ports-and-adapters-architecture.md)

## Decision

To align with the established architectural standards within the NeuRIS project, the decision has been made to introduce
the Hexagonal Architecture in the backend service of our team. This architectural style emphasizes the separation of
concerns, making it easier to adapt to changes, test components in isolation, and integrate with external systems.

## Consequences

Using this architecture will provide us with:

- **Modularity:** The Hexagonal Architecture promotes a modular design, allowing for the isolation of business logic from external dependencies, such as databases, APIs, and third-party services. This modularity enhances code maintainability and ease of understanding.
- **Testability:** With clear separation between the application core and external dependencies, unit testing becomes more straightforward. This architecture facilitates the use of test doubles and mocks, enabling comprehensive testing of business logic without relying on external systems.
- **Flexibility:** The Hexagonal Architecture enables easier adaptation to change in external systems or business requirements. Ports and Adapters can be modified or replaced without affecting the core business logic, providing a flexible foundation for future enhancements.
- **Consistency:** Aligning with other teams in the NeuRIS project ensures consistency across services. Developers familiar with the Hexagonal Architecture in other services will find it easier to navigate and contribute to our backend codebase.

The architecture is tested using [ArchUnit](https://www.archunit.org).






