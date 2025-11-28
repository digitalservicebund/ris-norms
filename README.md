# RIS Norms

| All modules                                                                                                                                                                             | Frontend                                                                                                                                                                                                                      | Backend                                                                                                                                                                                                                             |
| --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [![Pipeline](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml) | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-frontend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-frontend) | [![Quality Gate Status Backend](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-backend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-backend) |

This repository contains a web app supporting the Federal Documentation of Norms (DE: ["Normendokumentation"](https://www.bundesjustizamt.de/DE/Themen/Rechtsetzung/Normendokumentation/Normendokumentation_node.html)). It is part of NeuRIS. You can learn more about NeuRIS on our [website](https://digitalservice.bund.de/en/projects/new-legal-information-system).

## Quickstart

Clone this repo and initialize the included submodule:

```bash
git clone --recursive git@github.com:digitalservicebund/ris-norms.git
```

If you're already familiar with our stack and the project, here is a list of the most important commands for running frequent tasks. You will find [more detailed instructions below](#prerequisites).

### Shared Schema via Git Submodule

This repository includes a Git submodule for the shared LegalDocML schema extension, located at:

`LegalDocML.de/ris-norms-ldml-schema-extensions`

The submodule points to the [digitalservicebund/ris-norms-ldml-schema-extensions](https://github.com/digitalservicebund/ris-norms-ldml-schema-extensions) repository and contains schema extensions for LegalDocML.de

To initialize or update the submodule:

```bash
git submodule update --init --recursive
```

### Test user credentials

When running the application locally, use the following test user credentials:

- Username: `jane.doe`
- Password: `test`

### Running backend + frontend separately

```sh
# Run Docker containers (working dir: project root)
docker compose -f docker-compose-services.yaml up -d

# Run backend (working dir: `./backend`) with 2 modes:
# 1. For local development (without E2E seeds)
./gradlew bootRun
# 2. For running the E2E tests (including the seeds for them)
./gradlew bootRun --args='--spring.profiles.active=local,e2e'

# Install frontend dependencies and run frontend (working dir: `./frontend`)
npm ci --ignore-scripts
node --run dev
```

You can open the frontend at <http://localhost:5173>.

### Running a full, containerized build locally

```sh
# (working dir: project root)
docker compose up -d
```

You can open the frontend at <http://localhost:8080>.

When wanting to run the e2e tests:
```sh
# (working dir: project root)
RUN_E2E_TESTS=true docker compose up -d
```

### Testing

Backend:

```sh
./gradlew test            # Unit tests
./gradlew integrationTest # Integration tests
./gradlew build           # Build with all checks, including tests and code style
```

Frontend:

```sh
node --run test       # Unit tests (once)
node --run test:watch # Unit tests (watch mode)
node --run test:a11y  # Accessibility tests
```

E2E tests (included in the frontend module, backend and frontend must be [running separately](#running-backend--frontend-separately)):

```sh
node --run test:browsers                             # E2E tests in Chrome and Firefox
node --run test:e2e -- --project <chromium|firefox>  # E2E tests for a specific browser
node --run test:e2e -- --ui                          # Opens the Playwright UI for testing
```

### Code style & quality

Backend:

```sh
./gradlew spotlessApply   # Format code
```

Frontend:

```sh
node --run style:fix    # Check code conventions + formatting, attempt to fix
node --run typecheck    # Check TypeScript validity
```

### Building

Backend:

```sh
./gradlew build
```

Frontend:

```sh
node --run build

# Optionally, preview the build output (requires a running backend):
node --run preview
```

## Navigating the repository

This is a mono-repository containing:

| Module                              | Notes                                                                                                                          |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| [`.github`](./github)               | GitHub configuration, including automated pipelines                                                                            |
| [`backend`](./backend/)             | The backend service (Java + Spring Boot)                                                                                       |
| [`doc`](./doc/)                     | Additional documentation, including [Architecture Decision Records (ADRs)](./doc/adr/) and [API specifications](./backend/)    |
| [`frontend`](./frontend/)           | A browser-based interface for users (TypeScript + Vue + Tailwind)                                                              |
| [`LegalDocML.de`](./LegalDocML.de/) | Schemas, examples, test data, and custom extensions to [LegalDocDML.de](https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de) |
| [`local`](./local/)                 | Additional setup for local development                                                                                         |
| [`regex`](./regex/)                 | Utilities for creating regex schemas                                                                                           |

## Prerequisites

To build and run the application, you'll need:

- Docker, for infrastructure or running a containerized version of the entire application locally
- A Java 21-compatible JDK
- A recent version of Node (you'll find the exact version we're using [here](./frontend/.node-version))

If you would like to make changes to the application, you'll also need:

- [`jq`](https://jqlang.org/), for parsing license data
- [`talisman`](https://thoughtworks.github.io/talisman/), for preventing accidentially committing sensitive data
- [`lefthook`](https://lefthook.dev/), for running Git hooks
- [`gh`](https://cli.github.com/), for checking the pipeline status before pushing
- (optional) [`adr-tools`](https://github.com/npryce/adr-tools), for scaffolding new ADRs
- (optional) [`nvm`](https://github.com/nvm-sh/nvm), for managing Node versions

If you use [Homebrew](https://brew.sh/), you can install all of them like this:

```sh
brew install openjdk@21 jq talisman lefthook gh adr-tools nvm
brew install --cask docker # or `brew install docker` if you don't want the desktop app
```

Once you installed the prerequisites, make sure to initialize Git hooks. This will ensure any code you commit follows our coding standards, is properly formatted, and has a commit message adhering to our conventions:

```sh
lefthook install
```

Finally, there are some environment variables that need to be set locally. As a starting point, copy the `frontend/.env.local.example` file, and rename it to `.env.local`. Learn more about environment variables [here](./frontend/README.md).

## Learn more

You will find more information about each module in the respective folders. If you're getting started, the READMEs of the [backend](./backend/README.md) and [frontend](./frontend/README.md) will be the most relevant resources.

## License checking

When installing dependencies, make sure they are licensed under one of the [allowed licenses](./allowed-licenses.json). This will be checked in the pipeline for both frontend and backend dependencies. The pipeline will fail if licenses not included in the list are used by any dependency.

## Contributing

If you would like to contribute, check out [`CONTRIBUTING.md`](./CONTRIBUTING.md). Please also consider our [Code of Conduct](./CODE_OF_CONDUCT.md).

## Additional resources

- 🔒 [RIS Reports](https://github.com/digitalservicebund/ris-reports) (additional documentation, including architecture diagrams and JavaDocs)
- 🔒 [Infrastructure](https://github.com/digitalservicebund/ris-norms-infra)
- 🔒 [Importer for migrated data](https://github.com/digitalservicebund/ris-norms-migration-import/)
- [LDML.de metadata custom namespace](https://github.com/digitalservicebund/ris-norms-ldml-schema-extensions)
