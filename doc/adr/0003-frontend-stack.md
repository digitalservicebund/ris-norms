# 3. Frontend stack

Date: 2023-01-22

## Status

Accepted

## Context

> **Note**
>
> For a more detailed discussion of the decision made in this ADR, check out the [RFC in our (internal) Confluence](https://digitalservicebund.atlassian.net/wiki/spaces/VER/pages/866615297/RFC+0001+-+Frontend+stack).

We need to decide on the set of frameworks, libraries, and tools for the initial setup of our frontend. Our goal is a stack that fulfills the following criteria:

- **Reliability:** We focus on proven technologies that have been widely adopted in the industry.

- **Ease of use:** We are looking for technologies that enhance developer productivity, making our day-to-day work more efficient.

- **Familiarity:** Where applicable, we aim to rely on knowledge we already built in the team, as well as NeuRIS and the DigitalService more broadly.

- **Future proof:** We choose “boring technologies” that have been around for a (relatively) long time, increasing the likelihood that these choices will not become obsolete anytime soon. We also try to stay close to established standards wherever possible.

- **Testability:** Our stack needs to support thorough automated testing to ensure quality.

- **Compliance with the requirements for client applications in the federal architectural guidelines**, specifically TIAS-25 (websites should use open and standardized technologies, in particular HTML5 and CSS, proprietary technologies are not allowed) and TIAS-26 (interactive content should use at least ECMAScript 2017, TypeScript is allowed).

## Decision

The frontend will be a single page application (SPA) to allow for high levels of interactivity, ease of hosting, and modularity.

We will be using the following frameworks and languages:

- **TypeScript** as the client-side programming language
- **Vue** for the UI
- **Vue router** for routing
- **Pinia** as the data store
- **Immer** for immutable data structures
- **Tailwind CSS** for styling

Additional tooling:

- The latest **LTS of Node** and its corresponding NPM version as the engine for all frontend-related tooling and dependency management.
- **Vite** as our development server and bundler
- **Vitest** for unit testing
- **Playwright** for end-to-end testing
- **ESLint** for code quality checking and enforcing code standards
- **Prettier** for formatting
- **license-checker** for enforcing requirements for licenses in dependencies.
- **Histoire** for isolated component development

## Consequences

This stack should satisfy all our technical requirements and preferences, allowing us to work effectively while complying with the federal architectural guidelines.
