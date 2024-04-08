# Backend module

Backend service built with Spring Boot.

## Prerequisites

Java 21, Docker for building + running the containerized application:

```bash
brew install openjdk@21
brew install --cask docker # or just `brew install docker` if you don't want the Desktop app
```

Install node for Spotless with Prettier being able to run.

## Running and developing

Set up and boot the postgres database and the redis database (from the project root):

```sh
docker compose up postgres14 redis
```

You can then start the backend with two different options:

1. Running the `bootRun` Gradle task.
2. Creating a Run/Debug config in your IDE with Spring support using the profile `local`

## Tests

The project has distinct unit and integration test sets.

**To run just the unit tests:**

```bash
./gradlew test
```

**To run the integration tests:**

```bash
./gradlew integrationTest
```

**Note:** Running integration tests requires passing unit tests (in Gradle terms: integration tests depend on unit
tests), so unit tests are going to be run first. In case there are failing unit tests we won't attempt to continue
running any integration tests.

**To run integration tests exclusively, without the unit test dependency:**

```bash
./gradlew integrationTest --exclude-task test
```

Denoting an integration test is accomplished by using a JUnit 5 tag annotation: `@Tag("integration")`.

Furthermore, there is another type of test worth mentioning. We're
using [ArchUnit](https://www.archunit.org/getting-started)
for ensuring certain architectural characteristics, for instance making sure that there are no cyclic dependencies.

## Formatting

Java source code formatting must conform to the [Google Java Style](https://google.github.io/styleguide/javaguide.html).
Consistent formatting, for Java as well as various other types of source code, is being enforced
via [Spotless](https://github.com/diffplug/spotless).

**Check formatting:**

```bash
./gradlew spotlessCheck
```

**Autoformat sources:**

```bash
./gradlew spotlessApply
```

## Code quality analysis

Continuous code quality analysis is performed in the pipeline upon pushing to trunk; it requires a
token provided as `SONAR_TOKEN` repository secret that needs to be obtained from https://sonarcloud.io.

**To run the analysis locally:**

```bash
SONAR_TOKEN=[sonar-token] ./gradlew sonarqube
```

Go to [https://sonarcloud.io](https://sonarcloud.io/dashboard?id=digitalservicebund_ris-norms-backend)
for the analysis results.

## License Scanning

License scanning is performed as part of the pipeline's `build` job. Whenever a production dependency
is being added with a yet unknown license the build is going to fail.

**To run a scan locally:**

```bash
./gradlew checkLicense
```

## Javadoc comments check

We aim to write Javadoc comments to at least all public classes and methods. To fulfill this goal we use checkstyle,
which is integrated not only into gradle but into lefthook (pre-commit)

**To run a check locally:**

```bash
./gradlew check
```
