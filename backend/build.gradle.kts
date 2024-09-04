import com.adarshr.gradle.testlogger.theme.ThemeType
import com.github.jk1.license.filter.DependencyFilter
import com.github.jk1.license.filter.LicenseBundleNormalizer
import com.github.jk1.license.render.CsvReportRenderer
import com.github.jk1.license.render.ReportRenderer

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
    id("checkstyle")
    alias(libs.plugins.sentry)
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
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.cloud.starter.kubernetes.client.config)
    implementation(libs.postgresql)
    implementation(libs.flyway.core)
    implementation(libs.flyway.postgres)
    implementation(libs.jose4j)
    implementation(libs.prometheus)
    implementation(libs.spring.starter.data.redis)
    implementation(libs.spring.session.data.redis)
    implementation(libs.saxon.he)
    implementation(libs.bouncycastle.bcprov)
    implementation(libs.bouncycastle.bcpkix)

    compileOnly(libs.lombok)

    developmentOnly(libs.spring.boot.devtools)

    annotationProcessor(libs.lombok)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.security.test)
    testImplementation(libs.archunit.junit5)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.testcontainers.core)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.postgres)
}

tasks {
    register<Test>("integrationTest") {
        description = "Runs the integration tests."
        group = "verification"
        useJUnitPlatform {
            includeTags("integration")
        }
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
        dependsOn("integrationTest", "test") // All tests are required to run before generating a report.
    }

    jar { // We have no need for the plain archive, thus skip creation for build speedup!
        enabled = false
    }

    getByName("sonar") {
        dependsOn("jacocoTestReport")
    }

    test {
        useJUnitPlatform { excludeTags("integration") }
    }

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

    // To avoid having the warning with org.apache.logging.log4j:log4j-core [2.17.1 -> 2.23.0]
    // See https://github.com/ben-manes/gradle-versions-plugin/issues/686#issuecomment-1225322252
    withType(com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask::class) {
        checkBuildEnvironmentConstraints = false
    }

    named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
        args("--spring.profiles.active=local")
    }

    processResources {
        // include the ldml.de schema
        from("../ldml-extension") {
            includeEmptyDirs = false
            include("**/*.xsd")
            into("./schema")
        }
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
        property("sonar.projectKey", "digitalservicebund_ris-norms-backend")
        property("sonar.organization", "digitalservicebund")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

licenseReport {
    allowedLicensesFile = File("$projectDir/../allowed-licenses.json")
    renderers = arrayOf<ReportRenderer>(CsvReportRenderer("backend-licence-report.csv"))
    filters = arrayOf<DependencyFilter>(LicenseBundleNormalizer())
}

tasks.named<Checkstyle>("checkstyleMain") {
    source = sourceSets["main"].allJava
    configFile = rootProject.file("checkstyle/config-main.xml")
}

tasks.named<Checkstyle>("checkstyleTest") {
    source = sourceSets["test"].allJava
    configFile = rootProject.file("checkstyle/config-test.xml")
}

if (System.getProperty("spring.profiles.active") == "staging") {
    sentry {
        // Generates a JVM (Java, Kotlin, etc.) source bundle and uploads your source code to Sentry.
        // This enables source context, allowing you to see your source
        // code as part of your stack traces in Sentry.
        // includeSourceContext = true

        // Temporarily disabled since it didn't work when building with gradle in the docker container
        includeSourceContext = false

        org = "digitalservice"
        projectName = "ris-norms"
        debug.set(true)
        authToken = System.getenv("SENTRY_AUTH_TOKEN")
    }
}
