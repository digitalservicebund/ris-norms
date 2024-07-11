# 10. How to use exceptions in the backend

Date: 2024-06-28

## Status

Accepted

## Context
So far we used different approaches when to use Optionals, throw checked exceptions or unchecked exceptions. This also led
to using exceptions from other layers, which was violating the hexagonal architecture approach. We want to streamline our approach.

## Decision
Layer-Specific Exceptions
* By having each layer (api, database components in the adapter layer and the application layer) define its own exceptions, we achieve a clear separation of concerns. This ensures that exceptions are relevant to the specific context in which they occur. We also discussed and refused the idea of a common package for exceptions so that we can reuse exceptions in different layers which is not necessary with the current approach.
* Avoiding a common exception package prevents mixing unrelated exceptions and promotes a more organized codebase.

Custom Runtime Exceptions
* Throwing custom runtime exceptions in the application layer allows us to encapsulate domain-specific logic. These exceptions can be tailored to the norms project’s requirements.
* Using @ControllerAdvice to handle these exceptions ensures consistent error handling across the application. Since all requests go through the api component we can centralize the exception handling logic and provide a single point of control.

Output Ports
* Returning optionals from output ports aligns with functional programming principles. Optionals allow for more elegant handling of absence or optional results.
* Translating exceptions thrown in output ports (if needed) to application layer exceptions maintains a clean boundary between layers. It avoids leaking implementation details and promotes a clear contract.
* The output port is not allowed not have a dependency on the application layer’s exceptions.

Input Ports
* Allowing input ports to depend on application layer exceptions in the ArchUnit tests acknowledges that input ports may need to handle specific application-related errors.
* This flexibility ensures that input ports can react appropriately to exceptions without violating the hexagonal architecture’s boundaries.

## Consequences
In general, we support the hexagonal architecture with a strong separation of layers while promoting consistency and clarity.
But we have to be careful not to create an excessive number of custom exception classes that might introduce unnecessary complexity.
