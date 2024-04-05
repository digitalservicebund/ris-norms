package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.application.service.XmlDocumentService;

class TargetLawDtoTest {

  private static Validator validator;

  @BeforeAll
  public static void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testAllConstraintsAreMet() {
    // Given
    final UUID id = UUID.randomUUID();
    final String eli = "someEli";
    final String title = "title";
    final String xml = "<test></test>";
    final String fna = "4711";
    final String shortTitle = "T";

    final TimeMachineService timeMachineService = new TimeMachineService(new XmlDocumentService()));

    // When
    final TargetLawDto targetLawDto =
        TargetLawDto.builder()
            .id(id)
            .eli(eli)
            .title(title)
            .xml(timeMachineService.stringToXmlDocument(xml))
            .fna(fna)
            .shortTitle(shortTitle)
            .build();
    final Set<ConstraintViolation<TargetLawDto>> violations = validator.validate(targetLawDto);

    // Then
    assertThat(violations).isEmpty();
  }

  @Test
  void testMissingNotNullProperties() {
    // Given
    final TargetLawDto targetLawDto = new TargetLawDto();

    // When
    final Set<ConstraintViolation<TargetLawDto>> violations = validator.validate(targetLawDto);

    // Then
    assertThat(violations)
        .hasSize(5)
        .extracting(
            violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
        .containsExactlyInAnyOrder(
            "eli must not be null",
            "title must not be null",
            "xml must not be null",
            "fna must not be null",
            "shortTitle must not be null");
  }
}
