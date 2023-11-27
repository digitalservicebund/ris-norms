import com.github.jk1.license.filter.LicenseBundleNormalizer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spotless)
    id("jacoco")
    id("jacoco-report-aggregation")
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.dependency.license.report)
    alias(libs.plugins.test.logger)
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.versions)
}

group = "de.bund.digitalservice"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

testlogger { theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA }

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.reactor.kotlin.extensions)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.coroutines.reactor)

    // CVE-2022-1471
    implementation(libs.snakeyaml)

    // CVE-2023-34062
    implementation(libs.reactor.netty.http)

    developmentOnly(libs.spring.boot.devtools)
}

testing {
    @Suppress("UnstableApiUsage")
    suites {
        val test by getting(JvmTestSuite::class) {
            testType.set(TestSuiteType.UNIT_TEST)
            useJUnitJupiter()
            dependencies {
                implementation(libs.spring.boot.starter.test) {
                    exclude("org.mockito", "mockito-core")
                    because("Use MockK instead of Mockito since it is better suited for Kotlin")
                }
                implementation(libs.springmockk)
                implementation(libs.reactor.test)
                implementation(libs.spring.security.test)
                implementation(libs.archunit.junit5)
            }
        }

        register("integrationTest", JvmTestSuite::class) {
            testType.set(TestSuiteType.INTEGRATION_TEST)
            dependencies {
                implementation(project())
                implementation(libs.spring.boot.starter.test) {
                    exclude("org.mockito", "mockito-core")
                    because("Use MockK instead of Mockito since it is better suited for Kotlin")
                }
                implementation(libs.springmockk)
                implementation(libs.reactor.test)
                implementation(libs.spring.security.test)
            }
            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
        }
    }
}

configurations {
    val integrationTestImplementation by getting {
        extendsFrom(implementation.get())
    }
    val aggregateCodeCoverageReportResults by getting {
        extendsFrom(implementation.get())
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    check {
        dependsOn(testCodeCoverageReport, getByName("integrationTestCodeCoverageReport"))
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

    bootBuildImage {
        val containerRegistry = System.getenv("CONTAINER_REGISTRY") ?: "ghcr.io"
        val containerImageName =
            System.getenv("CONTAINER_IMAGE_NAME")
                ?: "digitalservicebund/${rootProject.name}"
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

    getByName("sonar") {
        dependsOn(testCodeCoverageReport, getByName("integrationTestCodeCoverageReport"))
    }
}

reporting {
    reports {
        @Suppress("UnstableApiUsage")
        withType(JacocoCoverageReport::class) {
            reportTask.configure {
                classDirectories.setFrom(
                    files(
                        classDirectories.files.map {
                            fileTree(it) {
                                exclude("**/ApplicationKt**")
                            }
                        },
                    ),
                )
            }
        }

        @Suppress("UnstableApiUsage")
        create<JacocoCoverageReport>("aggregateCodeCoverageReport") {
            testType.set(TestSuiteType.UNIT_TEST)
            reportTask {
                executionData.from(
                    configurations["aggregateCodeCoverageReportResults"]
                        .incoming.artifactView {
                            lenient(true)
                            withVariantReselection()
                            attributes {
                                attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.VERIFICATION))
                                attribute(TestSuiteType.TEST_SUITE_TYPE_ATTRIBUTE, objects.named(TestSuiteType.INTEGRATION_TEST))
                                attribute(VerificationType.VERIFICATION_TYPE_ATTRIBUTE, objects.named(VerificationType.JACOCO_RESULTS))
                                attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, ArtifactTypeDefinition.BINARY_DATA_TYPE)
                            }
                        }.files,
                )
            }
        }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "digitalservicebund_ris-norms-time-machine")
        property("sonar.organization", "digitalservicebund")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

spotless {
    kotlin {
        ktlint()
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

licenseReport {
// If there's a new dependency with a yet unknown license causing this task to fail
// the license(s) will be listed in build/reports/dependency-license/dependencies-without-allowed-license.json
    allowedLicensesFile = File("$projectDir/../allowed-licenses.json")
    filters =
        arrayOf(
            // With second arg true we get the default transformations:
            // https://github.com/jk1/Gradle-License-Report/blob/7cf695c38126b63ef9e907345adab84dfa92ea0e/src/main/resources/default-license-normalizer-bundle.json
            LicenseBundleNormalizer(null, true),
        )
}
