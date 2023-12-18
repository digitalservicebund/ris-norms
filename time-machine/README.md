# Time-Machine

The goal of the time machine is to apply modifications as defined in an [LDML_de](https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de) document.

Given the 
* amending law in LDML_de format and the
* target law in LDML_de format
it will produce the amended version of the target law.

Note that we're in the first iterations, there's not much to be expected.

# Development

## Prerequisites

* Java 17
* Kotlin 1.3

## Running via building and using a Startup Script

### Building the startup scripts from source
```bash
./gradlew install
```
The results can be found in the `./build/install/ris-norms-time-machine` folder.

### Running the script

On Linux / Mac run the `sh` script via

```bash
./build/install/ris-norms-time-machine/bin/ris-norms-time-machine
```
On Windows, use the batch file

```
./build/install/ris-norms-time-machine/bin/ris-norms-time-machine.bat
```

Running without any arguments or parameters will tell you what options exist.


### PATH

Note that using the time-machine in the [vscode-extension](../vscode-extension/README.md) requires the scripts to be in the system's PATH.

## Building (without tests)
```bash
./gradlew build -x test -x testCoverageReport
```

The results can then be found in the `./build` folder.

## Testing
For a single test run use
```bash
./gradlew test
```

For continuous testing (e.g. TDD), use
```bash
./gradlew test --continuous
```



----
🚧 The below stems from a placeholder, right now. Do not follow it! 🚧
----

# Kotlin Application Template

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
