package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.domain.entity.NormPublishState;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NormDtoTest {

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
    var xml = NormFixtures.loadTextFromDisk("SimpleNorm.xml");
    var guid = UUID.fromString("c01334e2-f12b-4055-ac82-15ac03c74c78");

    // When
    final NormDto normDto = NormDto.builder().xml(xml).guid(guid).build();
    final Set<ConstraintViolation<NormDto>> violations = validator.validate(normDto);

    // Then
    assertThat(violations).isEmpty();
  }

  @Test
  void testMissingNotNullProperties() {
    // Given
    final NormDto normDto = new NormDto();

    // When
    final Set<ConstraintViolation<NormDto>> violations = validator.validate(normDto);

    // Then
    assertThat(violations)
      .hasSize(1)
      .extracting(violation -> violation.getPropertyPath().toString() + " " + violation.getMessage()
      )
      .containsExactlyInAnyOrder("xml must not be null");
  }

  @Test
  void shouldBeUnpublishedByDefaultWhenCreatedUsingBuilder() {
    // Given
    final NormDto normDto = NormDto
      .builder()
      .xml(NormFixtures.loadTextFromDisk("SimpleNorm.xml"))
      .build();

    // Then
    assertThat(normDto.getPublishState()).isEqualTo(NormPublishState.UNPUBLISHED);
  }
}
