# Backend

The Norms backend is a [Spring Boot](https://docs.spring.io/spring-boot/index.html) application build with Java. We use [Postgres](https://www.postgresql.org/) as our database and [Keycloak](https://www.keycloak.org/) as our identity provider. Both run locally using [Docker](https://www.docker.com/). In a live system (staging + production), the backend also serves the [frontend](../frontend/).

The backend is implemented using [hexagonal architecture](https://github.com/digitalservicebund/ris-norms/blob/main/doc/adr/0005-use-hexagonal-architecture-in-backend.md).

## Running

Make sure your system meets the [prerequisites](../README.md#prerequisites). Note that you need to have Node installed and available on your `PATH`, even if you don't intend to make changes to the frontend, because we use Prettier for formatting our backend codebase.

Then, start the Docker services:

```sh
# in the project root
docker compose -f docker-compose-services.yaml up
```

And boot the backend:

```sh
# in ./backend
./gradlew bootRun
```

Alternatively, you can boot the backend by creating a run configuration in an IDE such as IntelliJ. In that case, make sure that you set the profile to `local` in order to seed the database with test data.

## Testing

### Unit tests

To run the unit tests:

```sh
./gradlew test
```

We're using [ArchUnit](https://www.archunit.org/getting-started) for ensuring certain architectural characteristics, for instance making sure that there are no cyclic dependencies. These tests are run as part of the unit tests.

### Integration tests

Denoting an integration test is accomplished by using a JUnit 5 tag annotation: `@Tag("integration")`.

To run the integration tests:

```sh
./gradlew integrationTest
```

## Code Quality and Documentation

### Formatting

Java source code formatting must conform to the [Prettier Java Plugin](https://github.com/jhipster/prettier-java) style. Consistent formatting, for Java as well as various other types of source code, is enforced via [Spotless](https://github.com/diffplug/spotless):

Check formatting:

```sh
./gradlew spotlessCheck # Check formatting
./gradlew spotlessApply # Apply formatting
```

### Code quality

Continuous code quality analysis is performed in the pipeline when pushing to `main` or opening a pull request. It requires a token provided as `SONAR_TOKEN` repository secret that needs to be obtained from <https://sonarcloud.io>.

To run the analysis locally:

```sh
SONAR_TOKEN=[sonar-token] ./gradlew sonarqube
```

Go to [https://sonarcloud.io](https://sonarcloud.io/dashboard?id=digitalservicebund_ris-norms-backend) for the analysis results.

### JavaDoc comments check

We aim to write JavaDoc comments to at least all public classes and methods. To fulfill this goal we use checkstyle, which is integrated not only into gradle but into lefthook (pre-commit).

To run a check locally:

```sh
./gradlew check
```

## Check for Outdated Libraries

If the following statement prints a number > 0, then there are outdated libraries:

```sh
./gradlew versionCatalogUpdate
jq .outdated.count build/dependencyUpdates/report.json
```
