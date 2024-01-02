package de.bund.digitalservice.template

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition
import org.junit.jupiter.api.Test

const val BASE_PACKAGE_PATH = "de.bund.digitalservice.ris.norms"
val allClasses = ClassFileImporter().importPackages("$BASE_PACKAGE_PATH..")

class ArchitectureFitnessTest {

  @Test
  fun `all packages are free of circluar dependencies`() {
    SlicesRuleDefinition.slices()
        .matching("$BASE_PACKAGE_PATH.(**)")
        .should()
        .beFreeOfCycles()
        .check(allClasses)
  }

  @Test
  fun `all methods annotated as test are within a test class for correct execution`() {
    methods()
        .that()
        .areAnnotatedWith(Test::class.java)
        .should()
        .beDeclaredInClassesThat()
        .haveSimpleNameEndingWith("Test")
        .check(allClasses)
  }
}
