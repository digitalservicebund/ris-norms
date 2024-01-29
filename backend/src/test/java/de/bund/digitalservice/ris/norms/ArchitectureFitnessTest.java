package de.bund.digitalservice.ris.norms;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.simpleNameEndingWith;
import static com.tngtech.archunit.lang.conditions.ArchConditions.beInterfaces;
import static com.tngtech.archunit.lang.conditions.ArchConditions.bePublic;
import static com.tngtech.archunit.lang.conditions.ArchConditions.notImplement;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption.Predefined;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

class ArchitectureFitnessTest {

  static JavaClasses classes;

  static final String BASE_PACKAGE = "de.bund.digitalservice.ris.norms";

  static final String APPLICATION_LAYER_PACKAGES = BASE_PACKAGE + ".application..";

  static final String INPUT_PORT_LAYER_PACKAGES = BASE_PACKAGE + ".application.port.input";
  static final String OUTPUT_PORT_LAYER_PACKAGES = BASE_PACKAGE + ".application.port.output";
  static final String SERVICE_LAYER_PACKAGES = BASE_PACKAGE + ".application.service";

  static final String ADAPTER_LAYER_PACKAGES = BASE_PACKAGE + ".adapter..";
  static final String DOMAIN_LAYER_PACKAGES = BASE_PACKAGE + ".domain..";
  static final String ENTITY_LAYER_PACKAGES = BASE_PACKAGE + ".domain.entity";
  static final String VALUE_LAYER_PACKAGES = BASE_PACKAGE + ".domain.value";

  @BeforeAll
  static void setUp() {
    classes =
        new ClassFileImporter()
            .withImportOption(Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("de.bund.digitalservice.ris.norms");
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
  void domainClassesShouldOnlyDependOnDomainOrSpecificStandardLibraries() {
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(DOMAIN_LAYER_PACKAGES)
            .should()
            .onlyDependOnClassesThat(
                resideInAPackage(DOMAIN_LAYER_PACKAGES)
                    .or(
                        JavaClass.Predicates.resideInAnyPackage(
                            "kotlin..", "java..", "org.jetbrains.annotations..", "lombok..")));
    rule.check(classes);
  }

  @Test
  void allClassesOfDomainShouldBeSortedIntoPackages() {
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(DOMAIN_LAYER_PACKAGES)
            .should()
            .resideInAPackage(ENTITY_LAYER_PACKAGES)
            .orShould()
            .resideInAPackage(VALUE_LAYER_PACKAGES);
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

  @Test
  void controllerClassesShouldNotDependOnEachOther() {
    ArchRule rule =
        ArchRuleDefinition.noClasses()
            .that()
            .haveSimpleNameEndingWith("Controller")
            .should()
            .dependOnClassesThat()
            .resideInAPackage(ADAPTER_LAYER_PACKAGES)
            .andShould()
            .dependOnClassesThat()
            .areAnnotatedWith(Controller.class)
            .andShould()
            .dependOnClassesThat()
            .areAnnotatedWith(RestController.class);
    rule.check(classes);
  }

  @Test
  void applicationPackageShouldDependOnlyOnDomainAndSpecificExtras() {
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(APPLICATION_LAYER_PACKAGES)
            .should()
            .onlyDependOnClassesThat()
            .resideInAPackage(DOMAIN_LAYER_PACKAGES)
            .orShould()
            .resideInAnyPackage(APPLICATION_LAYER_PACKAGES)
            .orShould()
            .resideInAnyPackage("kotlin..", "java..", "org.jetbrains.annotations..")
            .orShould()
            .resideInAnyPackage("org.springframework.stereotype..", "org.slf4j..");
    rule.check(classes);
  }

  @Test
  void allClassesOfTheApplicationShouldBeSortedIntoTheFollowingPackages() {
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(APPLICATION_LAYER_PACKAGES)
            .should()
            .resideInAnyPackage(INPUT_PORT_LAYER_PACKAGES)
            .orShould()
            .resideInAPackage(OUTPUT_PORT_LAYER_PACKAGES)
            .orShould()
            .resideInAPackage(SERVICE_LAYER_PACKAGES);
    rule.check(classes);
  }

  @Test
  void portsAreInterfaces() {
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that(
                (resideInAPackage(INPUT_PORT_LAYER_PACKAGES).and(simpleNameEndingWith("UseCase")))
                    .or(
                        resideInAPackage(OUTPUT_PORT_LAYER_PACKAGES)
                            .and(simpleNameEndingWith("Port"))))
            .should(beInterfaces());
    rule.check(classes);
  }

  @Test
  void portsShouldNotDependOnEachOther() {
    SliceRule rule =
        SlicesRuleDefinition.slices()
            .matching(BASE_PACKAGE + ".application.port.(*)..")
            .should()
            .notDependOnEachOther();
    rule.check(classes);
  }

  @Test
  @Disabled
  void portsHaveASinglePublicMethodOnly() {

    DescribedPredicate<JavaClass> haveASingleMethodPredicate =
        new DescribedPredicate<>("have a single method") {
          @Override
          public boolean test(JavaClass input) {
            return input.getMethods().size() == 1;
          }
        };

    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAnyPackage(INPUT_PORT_LAYER_PACKAGES, OUTPUT_PORT_LAYER_PACKAGES)
            .should(ArchCondition.from((haveASingleMethodPredicate)))
            .andShould(bePublic());
    rule.check(classes);
  }

  //    @Test
  //    void portMethodsHaveASingleCommandOrQueryParameter() {
  //        val haveACommandParameter = haveAParameterWithTypeName("Command")
  //        val haveAQueryParameter = haveAParameterWithTypeName("Query")
  //
  //        ArchRule rule =  ArchRuleDefinition.methods()
  //                .that()
  //                .areDeclaredInClassesThat()
  //                .resideInAnyPackage(INPUT_PORT_LAYER_PACKAGES, OUTPUT_PORT_LAYER_PACKAGES)
  //                .should(haveASingleParameter().and(haveACommandParameter))
  //                .orShould(haveASingleParameter().and(haveAQueryParameter))
  //                .orShould(haveNoParameter());
  //        rule.check(classes);
  //    }

  @Test
  void applicationServicesDoNotImplementOutputPorts() {
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(SERVICE_LAYER_PACKAGES)
            .and()
            .haveSimpleNameEndingWith("Service")
            .should(
                notImplement(
                    resideInAPackage(OUTPUT_PORT_LAYER_PACKAGES)
                        .and(simpleNameEndingWith("Port"))));
    rule.check(classes);
  }

  @Test
  void adaptersShouldNotDependOnEachOther() {
    SliceRule rule =
        SlicesRuleDefinition.slices()
            .matching(BASE_PACKAGE + ".adapter.(*)..")
            .should()
            .notDependOnEachOther();
    rule.check(classes);
  }
}
