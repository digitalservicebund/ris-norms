import com.github.jk1.license.filter.DependencyFilter
import com.github.jk1.license.filter.LicenseBundleNormalizer
import com.github.jk1.license.render.CsvReportRenderer
import com.github.jk1.license.render.ReportRenderer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("application")
  alias(libs.plugins.kotlin.jvm)
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

repositories { mavenCentral() }

jacoco { toolVersion = libs.versions.jacoco.get() }

testlogger { theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA }

dependencies {
  implementation(platform(libs.kotlin.bom))
  implementation(libs.kotlin.stdlib.jdk8)
  implementation(libs.xml.unit.assertj3)
}

application { mainClass = "de.bund.digitalservice.ris.norms.timemachine.AppKt" }

testing {
  @Suppress("UnstableApiUsage")
  suites {
    val test by
        getting(JvmTestSuite::class) {
          testType.set(TestSuiteType.UNIT_TEST)
          useJUnitJupiter()
          dependencies {
            implementation(libs.archunit.junit5)
            implementation(libs.assertj.core)
          }
        }

    register("integrationTest", JvmTestSuite::class) {
      testType.set(TestSuiteType.INTEGRATION_TEST)
      dependencies { implementation(project()) }
      targets { all { testTask.configure { shouldRunAfter(test) } } }
    }
  }
}

configurations {
  val integrationTestImplementation by getting { extendsFrom(implementation.get()) }
  val aggregateCodeCoverageReportResults by getting { extendsFrom(implementation.get()) }
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "17"
    }
  }

  check { dependsOn(testCodeCoverageReport, getByName("integrationTestCodeCoverageReport")) }

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
                classDirectories.files.map { fileTree(it) { exclude("**/ApplicationKt**") } },
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
                .incoming
                .artifactView {
                  lenient(true)
                  withVariantReselection()
                  attributes {
                    attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.VERIFICATION))
                    attribute(
                        TestSuiteType.TEST_SUITE_TYPE_ATTRIBUTE,
                        objects.named(TestSuiteType.INTEGRATION_TEST))
                    attribute(
                        VerificationType.VERIFICATION_TYPE_ATTRIBUTE,
                        objects.named(VerificationType.JACOCO_RESULTS))
                    attribute(
                        ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE,
                        ArtifactTypeDefinition.BINARY_DATA_TYPE)
                  }
                }
                .files,
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
  kotlin { ktfmt() }
  kotlinGradle { ktfmt() }
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
        )
        .config(mapOf("keySeparator" to "="))
  }
}

licenseReport {
  allowedLicensesFile = File("$projectDir/../allowed-licenses.json")
  renderers = arrayOf<ReportRenderer>(CsvReportRenderer("backend-licence-report.csv"))
  filters = arrayOf<DependencyFilter>(LicenseBundleNormalizer(null, true))
}
