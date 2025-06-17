# Backend

The Norms backend is a [Spring Boot](https://docs.spring.io/spring-boot/index.html) application build with Java. We use [Postgres](https://www.postgresql.org/) as our database and [Keycloak](https://www.keycloak.org/) as our identity provider. Both run locally using [Docker](https://www.docker.com/). In a live system (e.g. production), the backend also serves the [frontend](../frontend/).

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

## Code quality and documentation

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

## Check for outdated libraries

If the following statement prints a number > 0, then there are outdated libraries:

```sh
./gradlew versionCatalogUpdate
jq .outdated.count build/dependencyUpdates/report.json
```

## Database Migrations

Database migration are done using flyway by creating new migration sql-scripts in `src/main/resources/db/migration`. For long-running migrations (> 2 minutes) the pipeline will fail on the deployment action.
If the migration takes this long the newly deployed image will take longer to be started and running than the check for a successful deployment is waiting. If such a pipeline failure happens you can check that
the deployment is currently running a migration by checking argocd. Once the migration has completed successful you can rerun the pipeline and that deployment step should succeed. This will probably need to be
repeated for every environment. If the migration takes longer than 90 minutes the pod will be restarted and the migration started anew. In this case we need to (temporarily) increase the maximal waiting time
for the startUp probe in the infra repository.

## Database Seeds

Database seeds are added using repeatable flyway migrations. These migrations are automatically created by gradle from the xml folders stored in `src/main/resources/db/data/x`. The name of the folder is used
for the name of the repeatable migration and the folder should contain the xml files (binary files are not currently supported) of the norm to add into the database.
We have different seed folders for "real" norms (that are very similar to existing norms), "synthetic" norms (created specifically to test certain scenarios) and fixtures for e2e tests.

## Key pair for signing/verifying ZIP

There is an external API developed for the e-Verkündung that accepts a ZIP file. A signature file must also be sent and it's validty will be checked.

For local development the following key pair is used:

```root-project/
├── local/
│   └── certs/
│       └── private-key.pem
│       └── certificate.pem
```

For how to create the signature refer to the README of [LegalDocML.de](../LegalDocML.de/1.8.1/README.md)
