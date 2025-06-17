import com.adarshr.gradle.testlogger.theme.ThemeType
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.jk1.license.filter.DependencyFilter
import com.github.jk1.license.filter.LicenseBundleNormalizer
import com.github.jk1.license.render.CsvReportRenderer
import com.github.jk1.license.render.ReportRenderer
import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone


buildscript { repositories { mavenCentral() } }

plugins {
    alias(libs.plugins.errorprone)
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

sourceSets {
    create("schematronToXslt") {}
}

val schematronToXsltCompileOnly by configurations.getting {
    extendsFrom(configurations.compileOnly.get())
}

dependencies {
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.security.oauth2.resource.server)
    implementation(libs.spring.security.oauth2.jose)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.cloud.starter.kubernetes.client.config)
    implementation(libs.shedlock)
    implementation(libs.shedlockJdbc)
    implementation(libs.postgresql)
    implementation(libs.flyway.core)
    implementation(libs.flyway.postgres)
    implementation(libs.jobrunr)
    implementation(libs.jose4j)
    implementation(libs.prometheus)
    implementation(libs.saxon.he)
    implementation(libs.bouncycastle.bcprov)
    implementation(libs.bouncycastle.bcpkix)
    implementation(libs.google.protobuf.java)
    implementation(platform(libs.aws.bom))
    implementation(libs.aws.s3)
    implementation(libs.squareup.okio.jvm)
    implementation(libs.tika.core)
    implementation(libs.jspecify)
    implementation(libs.tomcat.core)
    implementation(libs.tomcat.el)
    implementation(libs.tomcat.websocket)

    errorprone(libs.nullaway)
    errorprone(libs.errorprone)

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
    testImplementation(libs.testcontainers.keycloak)

    schematronToXsltCompileOnly(libs.schxslt)
    schematronToXsltCompileOnly(libs.saxon.he)
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone {
        disableAllChecks.set(true) // Disables all Error Prone checks except those you explicitly enable
        option("NullAway:OnlyNullMarked", "true") // Enable null tests only for null-marked code
        // option("NullAway:AnnotatedPackages", "de.bund.digitalservice.ris.norms") // Enable null check for all files in our package
        check("NullAway", CheckSeverity.ERROR) // Bump NullAway to error so the build fails on nullaway errors
    }
}

interface InjectedExecOps {
    @get:Inject val execOps: ExecOperations
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

    jar {
        // We have no need for the plain archive, thus skip creation for build speedup!
        enabled = false
    }

    getByName("sonar") {
        dependsOn("jacocoTestReport")
    }

    test {
        useJUnitPlatform { excludeTags("integration") }
    }

    // Needed to ignore io.sentry:sentry in the task. Adding isStable because default config was being overriden (so that we only get stable versions)
    withType(DependencyUpdatesTask::class) {
        fun isStable(version: String): Boolean {
            val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
            val regex = "^[0-9,.v-]+(-r)?$".toRegex()
            return stableKeyword || regex.matches(version)
        }
        gradleReleaseChannel = "current"
        revision = "release"
        rejectVersionIf { !isStable(candidate.version) || candidate.group == "io.sentry" }
        // To avoid having the warning with org.apache.logging.log4j:log4j-core [2.17.1 -> 2.23.0]
        // See https://github.com/ben-manes/gradle-versions-plugin/issues/686#issuecomment-1225322252
        checkBuildEnvironmentConstraints = false
    }

    named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
        args("--spring.profiles.active=local")
    }

    processResources {
        // include the ldml.de schema
        from("../LegalDocML.de") {
            includeEmptyDirs = false
            include("**/*.xsd")
            include("**/*.xsl")
            into("./LegalDocML.de")
        }

        // include the ldml.de schematron files and convert them to xsl files
        from("../LegalDocML.de") {
            includeEmptyDirs = false
            include("**/*.sch")
            into("./LegalDocML.de")

            doLast {
                fileTree(outputs.files.singleFile) {
                    include("**/*.sch")
                    map {
                        val injected = project.objects.newInstance<InjectedExecOps>()

                        injected.execOps.javaexec {
                            classpath = java.sourceSets["schematronToXslt"].compileClasspath
                            mainClass = "net.sf.saxon.Transform"

                            // load the xsl file for the sch -> xsl conversion from name.dmaus.schxslt:schxslt
                            val pipelineFile =
                                // find the jar file for name.dmaus.schxslt:schxslt and look into it as if it was a zip archive
                                zipTree(
                                    classpath
                                        .filter {
                                            it.name.startsWith("schxslt")
                                        }.singleFile,
                                ).filter {
                                    // file the pipeline xsl file for converting sch to xsl in this jar
                                    it.absolutePath.endsWith("/2.0/pipeline-for-svrl.xsl")
                                }.singleFile

                            val outputFile = it.parentFile.resolve(it.name.replace(".sch", ".xsl"))
                            args("-s:%s".format(it), "-xsl:%s".format(pipelineFile), "-o:%s".format(outputFile))
                        }
                    }
                }
            }
        }
    }

    processTestResources {
        // include the ldml.de fixture files
        from("../LegalDocML.de") {
            includeEmptyDirs = false
            include("**/*.xml")
            include("**/*.png")
            include("**/*.jpg")
            include("**/*.gif")
            include("**/*.pdf")
            into("./LegalDocML.de")
        }
    }
}

spotless {
    java {
        removeUnusedImports()
        prettier(
            mapOf(
                "prettier" to "3.5.3", // npm dependency
                "prettier-plugin-java" to "2.6.8", // npm dependency
            ),
        ).config(
            mapOf(
                "parser" to "java",
                "printWidth" to 100,
                "plugins" to listOf("prettier-plugin-java"),
            ),
        )
    }

    kotlinGradle {
        ktlint()
    }

    format("misc") {
        target(
            "**/*.js",
            "**/*.json",
            "**/*.md",
            "**/*.sh",
            "**/*.yml",
        )
        prettier(
            mapOf(
                "prettier" to "3.5.3", // npm dependency
                "prettier-plugin-sh" to "0.17.4", // npm dependency
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

if (System.getenv("SENTRY_AUTH_TOKEN") != null) {
    sentry {
        // Generates a JVM (Java, Kotlin, etc.) source bundle and uploads your source code to Sentry.
        // This enables source context, allowing you to see your source
        // code as part of your stack traces in Sentry.
        includeSourceContext = true

        org = "digitalservice"
        projectName = "ris-norms"
        authToken = System.getenv("SENTRY_AUTH_TOKEN")
    }
}
