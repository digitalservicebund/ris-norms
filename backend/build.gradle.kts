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
    alias(libs.plugins.gradle.git.properties)
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
    implementation(platform(libs.aws.bom))
    implementation(libs.aws.s3)
    implementation(libs.squareup.okio.jvm)
    implementation(libs.tika.core)
    implementation(libs.jspecify)
    implementation(libs.commons.lang3)
    implementation(libs.google.protobuf.java)
    implementation(libs.netty.codec.http)

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

gitProperties {
    dotGitDirectory = layout.projectDirectory.dir("..").dir(".git")
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

        // Before gradle 9.x.x, classpath and test classes from test source set where automatically used, now it must be explicitely done
        testClassesDirs =
            sourceSets.test
                .get()
                .output.classesDirs
        classpath = sourceSets.test.get().runtimeClasspath

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
        dependsOn("processDBFixtures")

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

    register("processDBFixtures") {
        val baseDir = fileTree("src/main/resources/db/data")
        val norms =
            baseDir.matching {
                include("**/rechtsetzungsdokument-1.xml")
            }

        inputs.files(norms)

        val outdir = layout.buildDirectory.dir("resources/main/db/data")
        outputs.dir(outdir)

        doLast {
            norms.files.forEach { rechtsetzungsdokumentFile ->
                val relativeNormFolder = rechtsetzungsdokumentFile.parentFile.relativeTo(baseDir.dir)
                logger.info("Creating db migration for $relativeNormFolder")
                val dbMigrationFile = file(outdir.get().file("$relativeNormFolder.sql"))

                dbMigrationFile.parentFile.mkdirs()
                dbMigrationFile.writeText("")

                val rechtsetzungsdokumentContent = rechtsetzungsdokumentFile.readText(Charsets.UTF_8)
                // we just look for the first thing looking like a norm expression eli as it should always be the one of the norm, and we don't need to parse the xml this way
                val eliNormExpression =
                    "eli/bund/[-a-z0-9]+/\\d{4}/(s[0-9]+[a-zäöüß]*|[0-9]+(-\\d+)?)/\\d{4}-\\d{2}-\\d{2}/\\d+/[a-z]{3}"
                        .toRegex()
                        .find(
                            rechtsetzungsdokumentContent,
                        )?.value

                if (eliNormExpression == null) {
                    throw GradleException("Could not find norm expression eli in ${rechtsetzungsdokumentFile.path}")
                }

                // To set the correct publish state the rechtsetzungsdokument can include a comment like "<!-- PUBLISH_STATE:UNPUBLISHED -->" that specifies which publish state should be set on the database.
                val publishState = "(?<=PUBLISH_STATE:)[A-Z]+".toRegex().find(rechtsetzungsdokumentContent)?.value ?: "PUBLISHED"

                dbMigrationFile.appendText("DELETE FROM dokumente WHERE eli_norm_expression = '$eliNormExpression';\n")
                dbMigrationFile.appendText("DELETE FROM norm_manifestation WHERE eli_norm_expression = '$eliNormExpression';\n")
                dbMigrationFile.appendText("DELETE FROM norm_expression WHERE eli_norm_expression = '$eliNormExpression';\n")

                // This can be used for sql-injections. So please do not include any sql in the sample files, we only do some very simple checking for the '-character ^^
                rechtsetzungsdokumentFile.parentFile.listFiles { file -> file.name.endsWith(".xml") }!!.forEach { file ->
                    val fileContent = file.readText(Charsets.UTF_8)

                    if (fileContent.contains("'")) {
                        throw GradleException("Content of ${file.path} includes a '. This will not create a valid database migration.")
                    }

                    dbMigrationFile.appendText("INSERT INTO dokumente (xml) VALUES ('$fileContent');\n")
                }

                dbMigrationFile.appendText(
                    "UPDATE norm_manifestation SET publish_state = '$publishState' WHERE eli_norm_expression = '$eliNormExpression';\n",
                )
            }
        }
    }
}

spotless {
    java {
        removeUnusedImports()
        prettier(
            mapOf(
                "prettier" to "3.6.2", // npm dependency
                "prettier-plugin-java" to "2.7.7", // npm dependency
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
        ktlint("1.5.0") // Spotless major version 8 not compatible with Ktlint default version 1.7.1
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
                "prettier" to "3.6.2", // npm dependency
                "prettier-plugin-sh" to "0.18.0", // npm dependency
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
        property(
            "sonar.coverage.exclusions",
            "**/config/**,**/Application.java",
        )
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
