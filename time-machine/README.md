# Kotlin Application Template

**WIP**

## Prerequisites

Kotlin 1.9 w/ Java 17, Docker for building + running the containerized application:

```bash
brew install openjdk@17
```

## Tests

This project has distinct unit and integration test suites.

**To run just the unit tests:**

```bash
./gradlew test
```

**To run the integration tests:**

```bash
./gradlew integrationTest
```

You can find the unit tests in `src/test/kotlin` and the integration tests in `src/integrationTest/kotlin`.

Furthermore, there is another type of test worth mentioning. We're
using [ArchUnit](https://www.archunit.org/getting-started)
for ensuring certain architectural characteristics, for instance making sure that there are no cyclic dependencies.

## Formatting & Linting

For linting and formatting Kotlin code [ktlint](https://ktlint.github.io) is used.

Consistent formatting for Kotlin, as well as various other types of source code (JSON, Markdown, Yaml, ...),
is being enforced via [Spotless](https://github.com/diffplug/spotless).

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
SONAR_TOKEN=[sonar-token] ./gradlew sonar
```

Go to [https://sonarcloud.io](https://sonarcloud.io/dashboard?id=digitalservicebund_ris-norms-time-machine)
for the analysis results.

## Vulnerability Scanning

**Warn:**
Out of date

Scanning container images for vulnerabilities is performed with [Trivy](https://github.com/aquasecurity/trivy)
as part of the pipeline's `build` job, as well as each night for the latest published image in the container
repository.

**To run a scan locally:**

Install Trivy:

```bash
brew install aquasecurity/trivy/trivy
```

```bash
./gradlew bootBuildImage
trivy image --severity HIGH,CRITICAL ghcr.io/digitalservicebund/ris-norms-time-machine:latest
```

As part of the automated vulnerability scanning we are generating a Cosign vulnerability scan record using Trivy,
and then use Cosign to attach an attestation of it to the container image, again
[signed with keyless signatures](https://github.com/sigstore/cosign/blob/main/KEYLESS.md) similar to signing the
container image itself. Using a policy engine in a cluster the vulnerability scan can be verified and for instance
running a container rejected if a scan is not current.

## License Scanning

License scanning is performed as part of the pipeline's `build` job. Whenever a production dependency
is being added with a yet unknown license the build is going to fail.

**To run a scan locally:**

```bash
./gradlew checkLicense
```
