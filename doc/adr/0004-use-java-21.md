# 4. Use Java 21

Date: 2024-01-29

## Status

Accepted

## Context

JDK 21, released as the latest Long-Term Support (LTS) version, was introduced in September 2023. This LTS designation
ensures long-term stability and support for our application until 2029 at least. JDK 21 provides an accumulated set of
recent language, API and JVM enhancements.

## Decision

We will use JDK 21 for our backend module. This move is motivated by the combination of LTS stability and the exciting
new features introduced in this release.

## Consequences

- **Feature Enhancements:** To mention few, JDK 21 introduces String templates and finalizes record patterns and pattern matching for switch statements, which can positively impact the development and performance of our backend service.
- **Performance Improvements:** As an LTS release, JDK 21 comes with optimizations that can lead to improved runtime performance and resource utilization, ensuring the long-term efficiency of our application.
- **Security Updates:** Staying up-to-date with the latest JDK LTS version ensures that our application benefits from ongoing security patches and enhancements, reducing the risk of vulnerabilities.
