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

class ArticleDtoTest {

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
    final String enumeration = "enumeration";
    final String eid = "eid";
    final String title = "title";

    // When
    final ArticleDto articleDto =
        ArticleDto.builder().id(id).enumeration(enumeration).eid(eid).title(title).build();
    final Set<ConstraintViolation<ArticleDto>> violations = validator.validate(articleDto);

    // Then
    assertThat(violations).isEmpty();
  }

  @Test
  void testMissingNotNullProperties() {
    // Given
    final ArticleDto articleDto = new ArticleDto();

    // When
    final Set<ConstraintViolation<ArticleDto>> violations = validator.validate(articleDto);

    // Then
    assertThat(violations)
        .hasSize(2)
        .extracting(
            violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
        .containsExactlyInAnyOrder("enumeration must not be null", "eid must not be null");
  }
}
