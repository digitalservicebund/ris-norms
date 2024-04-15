package de.bund.digitalservice.ris.norms.conventions;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.simpleNameEndingWith;
import static com.tngtech.archunit.lang.conditions.ArchConditions.*;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption.Predefined;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

class ArchitectureFitnessTest {

  static JavaClasses classes;

  static final String BASE_PACKAGE = "de.bund.digitalservice.ris.norms";

  static final String APPLICATION_LAYER_PACKAGES = BASE_PACKAGE + ".application..";

  static final String INPUT_PORT_LAYER_PACKAGES = BASE_PACKAGE + ".application.port.input";
  static final String OUTPUT_PORT_LAYER_PACKAGES = BASE_PACKAGE + ".application.port.output";
  static final String SERVICE_LAYER_PACKAGES = BASE_PACKAGE + ".application.service..";

  static final String ADAPTER_LAYER_PACKAGES = BASE_PACKAGE + ".adapter..";

  static final String REPOSITORY_LAYER_PACKAGES =
      BASE_PACKAGE + ".adapter.output.database.repository";
  static final String DOMAIN_LAYER_PACKAGES = BASE_PACKAGE + ".domain..";
  static final String ENTITY_LAYER_PACKAGES = BASE_PACKAGE + ".domain.entity";
  static final String VALUE_LAYER_PACKAGES = BASE_PACKAGE + ".domain.value";
  static final String EXCEPTIONS_LAYER_PACKAGES = BASE_PACKAGE + ".domain.exceptions";

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
    final String[] DOMAIN_LAYER_ALLOWED_PACKAGES =
        new String[] {
          "kotlin..",
          "java..",
          "javax.xml.xpath..",
          "org.jetbrains.annotations..",
          "lombok..",
          "org.w3c.dom.."
        };

    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(DOMAIN_LAYER_PACKAGES)
            .should(
                onlyDependOnClassesThat(
                    resideInAPackage(DOMAIN_LAYER_PACKAGES)
                        .or(
                            JavaClass.Predicates.resideInAnyPackage(
                                DOMAIN_LAYER_ALLOWED_PACKAGES))))
            .orShould(
                // the hashCode method of the Norm class needs to parse the w3c.dom.Document to a
                // string and for this we need some additional libraries
                be(Norm.class)
                    .and(
                        onlyDependOnClassesThat(
                            resideInAPackage(DOMAIN_LAYER_PACKAGES)
                                .or(
                                    JavaClass.Predicates.resideInAnyPackage(
                                        DOMAIN_LAYER_ALLOWED_PACKAGES))
                                .or(
                                    JavaClass.Predicates.resideInAnyPackage(
                                        "net.sf.saxon..", "javax.xml.transform..")))));
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
            .resideInAPackage(VALUE_LAYER_PACKAGES)
            .orShould()
            .resideInAPackage(EXCEPTIONS_LAYER_PACKAGES);

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
  void onlyRepositoryInterfacesAllowed() {
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(ADAPTER_LAYER_PACKAGES)
            .and()
            .haveSimpleNameEndingWith("Repository")
            .should()
            .beInterfaces();
    rule.check(classes);
  }

  @Test
  void implementationOutputPortsOnlyAllowedInTheAdapterPackageAsRepositories() {
    DescribedPredicate<JavaClass> predicate =
        JavaClass.Predicates.resideInAPackage(OUTPUT_PORT_LAYER_PACKAGES)
            .and(JavaClass.Predicates.INTERFACES);
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .implement(predicate)
            .should()
            .resideInAPackage(ADAPTER_LAYER_PACKAGES)
            .andShould()
            .beAnnotatedWith(Service.class);
    rule.check(classes);
  }

  @Test
  void implementationInputPortsOnlyAllowedInTheApplicationPackageAsServices() {
    DescribedPredicate<JavaClass> predicate =
        JavaClass.Predicates.resideInAPackage(INPUT_PORT_LAYER_PACKAGES)
            .and(JavaClass.Predicates.INTERFACES);
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .implement(predicate)
            .should()
            .resideInAPackage(APPLICATION_LAYER_PACKAGES)
            .andShould()
            .beAnnotatedWith(Service.class);
    rule.check(classes);
  }

  @Test
  void repositoryInRepositoryPackageAreInterfacesWhichExtendJpaRepository() {
    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(REPOSITORY_LAYER_PACKAGES)
            .and()
            .haveSimpleNameEndingWith("Repository")
            .should()
            .beInterfaces()
            .andShould()
            .beAssignableTo(JpaRepository.class);
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
  void portsHaveASinglePublicMethodOnly() {

    final HaveExactNumberOfMethods haveASingleMethod = new HaveExactNumberOfMethods(1);

    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAnyPackage(INPUT_PORT_LAYER_PACKAGES, OUTPUT_PORT_LAYER_PACKAGES)
            .and()
            .doNotHaveSimpleName("Query")
            .and()
            .doNotHaveSimpleName("Command")
            .and()
            .areNotAssignableTo(Exception.class)
            .should(ArchCondition.from((haveASingleMethod)))
            .andShould(bePublic());
    rule.check(classes);
  }

  @Test
  void portMethodsHaveASingleCommandOrQueryParameter() {

    final HaveAParameterWithTypeName haveACommandParameter =
        new HaveAParameterWithTypeName("Command");
    final HaveAParameterWithTypeName haveAQueryParameter = new HaveAParameterWithTypeName("Query");
    final HaveExactNumberOfParameters haveASingleParameter = new HaveExactNumberOfParameters(1);
    final HaveExactNumberOfParameters haveNoParameters = new HaveExactNumberOfParameters(0);

    ArchRule rule =
        ArchRuleDefinition.methods()
            .that()
            .areDeclaredInClassesThat()
            .resideInAnyPackage(INPUT_PORT_LAYER_PACKAGES, OUTPUT_PORT_LAYER_PACKAGES)
            .and()
            .doNotHaveName("equals")
            .should(
                ArchCondition.from((haveASingleParameter))
                    .and(ArchCondition.from(haveAQueryParameter)))
            .orShould(
                ArchCondition.from((haveASingleParameter))
                    .and(ArchCondition.from(haveACommandParameter)))
            .orShould(ArchCondition.from(haveNoParameters));
    rule.check(classes);
  }

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

  static class HaveExactNumberOfMethods extends DescribedPredicate<JavaClass> {

    private final int number;

    public HaveExactNumberOfMethods(final int number) {
      super("have exactly " + number + " method");
      this.number = number;
    }

    @Override
    public boolean test(JavaClass javaClass) {
      return javaClass.getMethods().size() == this.number;
    }
  }

  static class HaveAParameterWithTypeName extends DescribedPredicate<JavaMethod> {

    private final String typeName;

    public HaveAParameterWithTypeName(final String typeName) {
      super("have a parameter of type name " + typeName);
      this.typeName = typeName;
    }

    @Override
    public boolean test(JavaMethod javaMethod) {
      final List<String> matched =
          javaMethod.getParameters().stream()
              .map(it -> it.getRawType().getSimpleName())
              .filter(f -> Objects.equals(f, typeName))
              .toList();
      return !matched.isEmpty();
    }
  }

  static class HaveExactNumberOfParameters extends DescribedPredicate<JavaMethod> {

    private final int number;

    public HaveExactNumberOfParameters(final int number) {
      super("have exactly " + number + " parameters");
      this.number = number;
    }

    @Override
    public boolean test(JavaMethod javaMethod) {
      return javaMethod.getParameters().size() == this.number;
    }
  }
}
