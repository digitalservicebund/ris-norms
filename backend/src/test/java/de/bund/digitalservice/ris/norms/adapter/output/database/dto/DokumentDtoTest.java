package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DokumentDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testAllConstraintsAreMet() {
    // Given
    var xml = Fixtures.loadTextFromDisk(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
    );
    var guid = UUID.fromString("c01334e2-f12b-4055-ac82-15ac03c74c78");

    // When
    final DokumentDto dokumentDto = DokumentDto.builder().xml(xml).guid(guid).build();
    final Set<ConstraintViolation<DokumentDto>> violations = validator.validate(dokumentDto);

    // Then
    assertThat(violations).isEmpty();
  }

  @Test
  void testMissingNotNullProperties() {
    // Given
    final DokumentDto dokumentDto = new DokumentDto();

    // When
    final Set<ConstraintViolation<DokumentDto>> violations = validator.validate(dokumentDto);

    // Then
    assertThat(violations)
      .hasSize(1)
      .extracting(
        violation -> violation.getPropertyPath().toString() + " " + violation.getMessage()
      )
      .containsExactlyInAnyOrder("xml must not be null");
  }
}
