package de.bund.digitalservice.ris.norms;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption.Predefined;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

class ArchitectureFitnessTest {

  static JavaClasses classes;

  static final String APPLICATION_LAYER_PACKAGES = "de.bund.digitalservice.ris.norms.application..";
  static final String ADAPTER_LAYER_PACKAGES = "de.bund.digitalservice.ris.norms.adapter..";
  static final String DOMAIN_LAYER_PACKAGES = "de.bund.digitalservice.ris.norms.domain..";
  static final String RIS_PACKAGES = "de.bund.digitalservice.ris..";

  @BeforeAll
  static void setUp() {
    classes =
        new ClassFileImporter()
            .withImportOption(Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("de.bund.digitalservice.norms");
  }

  @Test
  void packagesShouldBeFreeOfCycles() {
    SliceRule rule =
        SlicesRuleDefinition.slices()
            .matching("de.bund.digitalservice.ris.norms.(*)")
            .should()
            .beFreeOfCycles();
    rule.check(classes);
  }

  @Test
  void domainClassesShouldOnlyDependOnDomainOrExternalLibClasses() {
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(DOMAIN_LAYER_PACKAGES)
            .should()
            .onlyDependOnClassesThat(
                JavaClass.Predicates.resideInAPackage(DOMAIN_LAYER_PACKAGES)
                    .or(JavaClass.Predicates.resideOutsideOfPackage(RIS_PACKAGES)));
    rule.check(classes);
  }

  @Test
  void controllerClassesShouldNotResideOutsideOfAdapterPackage() {
    ArchRule rule =
        ArchRuleDefinition.noClasses()
            .that()
            .areAnnotatedWith(Controller.class)
            .or()
            .areAnnotatedWith(RestController.class)
            .should()
            .resideOutsideOfPackage(ADAPTER_LAYER_PACKAGES);
    rule.check(classes);
  }
}
