# 0. Template

Date: 2025-02-14

## Status

Accepted

## Context
Our application requires a secure authentication mechanism. We are considering replacing our current session-based authentication with OAuth2.
With the OAuth2 based authentication we want to address the following problems:

1. Our identity provider (Keycloak) has no control over application access after the initial login
2. A session can last 12 hours or longer which gives an attacker a lot of time in case of a breach
3. There is no interoperability between Neuris systems meaning users would have to authenticate for each system
4. The OAuth2 concept as we used it was misused since a client was used instead of a resource server

## Decision
We have decided to implement OAuth2 for authentication instead of continuing with session-based authentication.
In detail this means:
- In our setup the frontend will serve as a OAuth2 client while the backend serves as a resource server
  - Thus, Keycloak regains control over application access
  - A token only lasts 1 minute reducing the impact on theft while still keeping the app usable
- We will use the OAuth2 authentication code flow with PKCE
  - PKCE is necessary since we don't have a confidential client which could be used with a client secret
- We will use Keycloak.js as client library because it is
  - lightweight
  - focused on what we need
  - is directly maintained by RedHat together with the Keycloak app.
- The backend uses the Spring Boot standard library for OAuth2 Resource Server which is recommended by the Keycloak developers
- We use JWTs (Java Web Token)
  - Which works without introspection (as opposed to opaque tokens) thus, we need fewer network calls
  - There is no sensitive data in the token we would have to obfuscate
- A threat modelling workshop is held to understand possible threats coming from this change

## Consequences
### Positive:
1. Since the frontend now handles the tokens developers have to keep in mind to keep the token in memory within a closure
2. The frontend has to be served before authentication thus allowing URLs serving the frontend while not exposing any sensitive URLs

### Negative:
1. Redis is removed since our authentication setup is now stateless and Redis was only used to synchronize state between pods

## Alternatives Considered
1. Continuing with Session-Based Authentication: While simpler to implement, it lacks the scalability and interoperability offered by OAuth2
2. BFF pattern or various proxys: The idea was rejected since it would add an extra service to maintain while not substantially offering additional security
