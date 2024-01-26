import com.adarshr.gradle.testlogger.theme.ThemeType
import com.github.jk1.license.filter.LicenseBundleNormalizer

buildscript { repositories { mavenCentral() } }

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    id("java")
    alias(libs.plugins.spotless)
    id("jacoco")
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.dependency.license.report)
    alias(libs.plugins.test.logger)
    alias(libs.plugins.versions)
    alias(libs.plugins.version.catalog.update)
}

group = "de.bund.digitalservice"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations { compileOnly { extendsFrom(annotationProcessor.get()) } }

repositories { mavenCentral() }

jacoco { toolVersion = libs.versions.jacoco.get() }

testlogger { theme = ThemeType.MOCHA }

dependencies {
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.web)

    compileOnly(libs.lombok)

    developmentOnly(libs.spring.boot.devtools)

    annotationProcessor(libs.lombok)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.security.test)
    testImplementation(libs.archunit.junit5)
    testImplementation(libs.mockito.junit.jupiter)
}

tasks {
    register<Test>("integrationTest") {
        description = "Runs the integration tests."
        group = "verification"
        useJUnitPlatform {
            includeTags("integration")
        }

        // So that running integration test require running unit tests first,
        // and we won"t even attempt running integration tests when there are
        // failing unit tests.
        dependsOn(test)
        finalizedBy(jacocoTestReport)
    }

    check {
        dependsOn("integrationTest")
    }

    bootBuildImage {
        val containerRegistry = System.getenv("CONTAINER_REGISTRY") ?: "ghcr.io"
        val containerImageName = System.getenv("CONTAINER_IMAGE_NAME") ?: "digitalservicebund/${rootProject.name}"
        val containerImageVersion = System.getenv("CONTAINER_IMAGE_VERSION") ?: "latest"

        imageName.set("$containerRegistry/$containerImageName:$containerImageVersion")
        builder.set("paketobuildpacks/builder-jammy-tiny")
        publish.set(false)

        docker {
            publishRegistry {
                username.set(System.getenv("CONTAINER_REGISTRY_USER") ?: "")
                password.set(System.getenv("CONTAINER_REGISTRY_PASSWORD") ?: "")
                url.set("https://$containerRegistry")
            }
        }
    }

    jacocoTestReport {
        // Jacoco hooks into all tasks of type: Test automatically, but results for each of these
        // tasks are kept separately and are not combined out of the box. we want to gather
        // coverage of our unit and integration tests as a single report!
        executionData.setFrom(
            files(
                fileTree(project.layout.buildDirectory) {
                    include("jacoco/*.exec")
                },
            ),
        )
        reports {
            xml.required = true
            html.required = true
        }
        dependsOn("integrationTest") // All tests are required to run before generating a report.
    }

    jar { // We have no need for the plain archive, thus skip creation for build speedup!
        enabled = false
    }

    getByName("sonar") {
        dependsOn("jacocoTestReport")
    }

    test { useJUnitPlatform { excludeTags("integration") } }

    withType(com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask::class) {
        fun isStable(version: String): Boolean {
            val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
            val regex = "^[0-9,.v-]+(-r)?$".toRegex()
            return stableKeyword || regex.matches(version)
        }
        gradleReleaseChannel = "current"
        revision = "release"
        rejectVersionIf { !isStable(candidate.version) }
    }
}

spotless {
    java {
        removeUnusedImports()
        googleJavaFormat()
    }

    kotlinGradle {
        ktlint()
    }

    format("misc") {
        target(
            "**/*.js",
            "**/*.json",
            "**/*.md",
            "**/*.properties",
            "**/*.sh",
            "**/*.yml",
        )
        prettier(
            mapOf(
                "prettier" to "2.6.1",
                "prettier-plugin-sh" to "0.7.1",
                "prettier-plugin-properties" to "0.1.0",
            ),
        ).config(mapOf("keySeparator" to "="))
    }
}

sonar {
    // NOTE: sonarqube picks up combined coverage correctly without further configuration from:
    // build/reports/jacoco/test/jacocoTestReport.xml
    properties {
        property("sonar.projectKey", "digitalservicebund_java-application-template")
        property("sonar.organization", "digitalservicebund")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

licenseReport {
    // If there's a new dependency with a yet unknown license causing this task to fail
    // the license(s) will be listed in build/reports/dependency-license/dependencies-without-allowed-license.json
    allowedLicensesFile = File("$projectDir/allowed-licenses.json")
    filters =
        arrayOf(
            // With second arg true we get the default transformations:
            // https://github.com/jk1/Gradle-License-Report/blob/7cf695c38126b63ef9e907345adab84dfa92ea0e/src/main/resources/default-license-normalizer-bundle.json
            LicenseBundleNormalizer(null, true),
        )
}
