# 14. Change authentication to OAuth2 with PKCE

Date: 2025-02-14

## Status

Accepted

## Context

Previously, our authentication mechanism was based on OAuth2 but was misconfigured. The backend acted as an OAuth2 client, redirecting users to [Keycloak](https://www.keycloak.org/), our identity provider, for authentication. Upon successful login, the backend retrieved the access token from Keycloak and stored it alongside the session in Redis. A session ID was sent to the frontend via the `Set-Cookie` header, and subsequent requests used this session ID for authentication.

However, this approach led to several problems:

1. Keycloak had no control over application access after the initial login.
2. A session could last 12 hours or longer, giving an attacker a lot of time in case of a breach.
3. Once we obtained a token from Keycloak, we did not refresh it. The token we stored expired after 5 minutes, but the expiration had no effect.
4. There was no interoperability between NeuRIS systems, meaning users would have to authenticate for each system.
5. OAuth2 was implemented incorrectly, with the backend being configured as an OAuth2 client instead of a resource server.

To address these issues, we are transitioning to a frontend-driven OAuth2 authentication model.

## Decision

We have decided to implement OAuth2 for authentication instead of continuing with session-based authentication. In detail this means:

**The frontend will serve as a OAuth2 client while the backend serves as a resource server.** Thus, Keycloak regains control over application access. A token will expire after 5 minutes, reducing the impact on theft while still keeping the app usable. The lifetime can be reduced further if necessary.

**We will use the OAuth2 authentication code flow with PKCE.** [PKCE](https://www.oauth.com/oauth2-servers/pkce/) is a suitable flow for our frontend, which is implemented as a [Single Page Application.](https://www.oauth.com/oauth2-servers/single-page-apps/) As such, it is not a confidential client and cannot contain secrets.

**We will use Keycloak.js as the client library in the frontend.** [Keycloak.js](https://github.com/keycloak/keycloak-js) is lightweight, focused on what we need, and directly maintained by RedHat, the developers of Keycloak.

**The backend will use the [Spring Boot standard library for OAuth2 Resource Servers.](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)** This libary is recommended by the Keycloak developers.

**We use JSON Web Tokens (JWTs).** JWTs are well-supported and self-contained, reducing implementation complexity and network calls. There is no sensitive data in the tokens we would have to obfuscate.

## Consequences

- The frontend is more actively involved in the authentication, including managing redirects, tokens, adding request headers for authorization.

- The frontend needs to be publicly accessible. Previously, all application routes, including the frontend, have been secured by the backend.

- Redis can be removed from our infrastructure, since we only needed it to track sessions and synchronize them between pods.

- Since we're making significant changes to a security-sensitive part of the application, we held a Threat Modeling workshop to understand the impact and mitigate risks. Findings and action items can be found in [Confluence](https://digitalservicebund.atlassian.net/wiki/spaces/VER/pages/1609629699/2025-02-06+Authentication+Threat+Modeling).

## Alternatives Considered

- Continuing with session-based authentication: While simpler to implement, it lacks the scalability and interoperability offered by OAuth2.

- BFF pattern or various proxys: The idea was rejected since it would add an extra service to maintain while not substantially improving security.
