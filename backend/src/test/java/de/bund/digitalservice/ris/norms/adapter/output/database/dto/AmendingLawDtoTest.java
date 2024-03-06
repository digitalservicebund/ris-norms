package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AmendingLawDtoTest {

  private static Validator validator;

  @BeforeAll
  public static void setUp() {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testAllConstraintsAreMet() {
    // Given
    final UUID id = UUID.randomUUID();
    final String eli = "someEli";
    final LocalDate publicationDate = LocalDate.now();
    final String title = "title";
    final String xml = "<test></test>";

    // When
    final AmendingLawDto amendingLawPrintDTO =
        AmendingLawDto.builder()
            .id(id)
            .eli(eli)
            .publicationDate(publicationDate)
            .title(title)
            .xml(xml)
            .build();
    final Set<ConstraintViolation<AmendingLawDto>> violations =
        validator.validate(amendingLawPrintDTO);

    // Then
    assertThat(violations).isEmpty();
  }

  @Test
  void testMissingNotNullProperties() {
    // Given
    final AmendingLawDto amendingLawDto = new AmendingLawDto();

    // When
    final Set<ConstraintViolation<AmendingLawDto>> violations = validator.validate(amendingLawDto);

    // Then
    assertThat(violations)
        .hasSize(4)
        .extracting(
            violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
        .containsExactlyInAnyOrder(
            "eli must not be null",
            "publicationDate must not be null",
            "title must not be null",
            "xml must not be null");
  }

  @Test
  void testSizeConstraints() {
    // Given
    final UUID id = UUID.randomUUID();
    final LocalDate publicationDate = LocalDate.now();
    final String xml = "<test></test>";
    final String invalidPrintGazette = "a".repeat(16);
    final String invalidDigitalMedium = "a".repeat(16);
    final String invalidPrintPage = "a".repeat(16);
    final String invalidDigitalEdition = "a".repeat(16);
    final String invalidEli = "a".repeat(256);
    final String invalidTitle = "a".repeat(256);

    // When
    final AmendingLawDto amendingLawDtoWithInvalidSizes =
        AmendingLawDto.builder()
            .id(id)
            .eli(invalidEli)
            .printAnnouncementGazette(invalidPrintGazette)
            .digitalAnnouncementMedium(invalidDigitalMedium)
            .printAnnouncementPage(invalidPrintPage)
            .digitalAnnouncementEdition(invalidDigitalEdition)
            .publicationDate(publicationDate)
            .title(invalidTitle)
            .xml(xml)
            .build();

    final Set<ConstraintViolation<AmendingLawDto>> violations =
        validator.validate(amendingLawDtoWithInvalidSizes);

    // Then
    assertThat(violations)
        .hasSize(6)
        .extracting(
            violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
        .containsExactlyInAnyOrder(
            "printAnnouncementPage size must be between 0 and 15",
            "printAnnouncementGazette size must be between 0 and 15",
            "digitalAnnouncementEdition size must be between 0 and 15",
            "title size must be between 0 and 255",
            "digitalAnnouncementMedium size must be between 0 and 15",
            "eli size must be between 0 and 255");
  }
}
