package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TargetLawDtoTest {

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
    final String title = "title";
    final String xml = "<test></test>";

    // When
    final TargetLawDto targetLawDto =
        TargetLawDto.builder().id(id).eli(eli).title(title).xml(xml).build();
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
        .hasSize(3)
        .extracting(
            violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
        .containsExactlyInAnyOrder(
            "eli must not be null", "title must not be null", "xml must not be null");
  }

  @Test
  void testSizeConstraints() {
    // Given
    final UUID id = UUID.randomUUID();
    final String eli = "a".repeat(256);
    final String title = "a".repeat(256);
    final String xml = "<test></test>";

    // When
    final TargetLawDto targetLawDtoWithInvalidSizes =
        TargetLawDto.builder().id(id).eli(eli).title(title).xml(xml).build();

    final Set<ConstraintViolation<TargetLawDto>> violations =
        validator.validate(targetLawDtoWithInvalidSizes);

    // Then
    assertThat(violations)
        .hasSize(2)
        .extracting(
            violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
        .containsExactlyInAnyOrder(
            "eli size must be between 0 and 255", "title size must be between 0 and 255");
  }
}
