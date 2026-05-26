package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Rechtsetzungsdokument;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.net.URI;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.UrlResource;

class LdmlDeValidatorTest {

  private final LdmlDeValidator ldmlDeValidator = new LdmlDeValidator(
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.8.2/schema/legalDocML.de.xsl")
      )
    ),
    Fixtures.getXsdSchemaService()
  );

  @Nested
  class validateXSDSchema {

    @Test
    void itShouldValidateAValidNorm() {
      // Given
      var norm = Fixtures.loadNormFromDisk("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05");

      // When // Then
      ldmlDeValidator.validateXSDSchema(norm); // Check that it doesn't throw
    }

    @Test
    void itShouldNotValidateAInvalidNorm() {
      // Given
      var norm = Fixtures.loadNormFromDisk(LdmlDeValidatorTest.class, "vereinsgesetz-xsd-invalid");

      // When // Then
      assertThatThrownBy(() -> ldmlDeValidator.validateXSDSchema(norm)).isInstanceOf(
        LdmlDeNotValidException.class
      );
    }
  }

  @Nested
  class parseAndValidateRegelungstext {

    @Test
    void itShouldParseAValidNorm() {
      // Given
      String xml = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
      );

      // When
      Regelungstext regelungstext = ldmlDeValidator.parseAndValidateRegelungstext(xml);

      // Then
      // we can't use Norm::getEli as it is not yet namespace-aware
      assertThat(
        NodeParser.getValueFromMandatoryNodeFromExpression(
          "//*[local-name()='FRBRManifestation']/*[local-name()='FRBRthis']/@value",
          regelungstext.getDocument()
        )
      ).isEqualTo(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-verkuendung-1.xml"
      );
    }

    @Test
    void itShouldThrowForInvalidNorms() {
      // Given
      String xml = Fixtures.loadTextFromDisk(
        LdmlDeValidatorTest.class,
        "vereinsgesetz-xsd-invalid/regelungstext-verkuendung-1.xml"
      );

      // When // Then
      assertThatThrownBy(() -> ldmlDeValidator.parseAndValidateRegelungstext(xml))
        .isInstanceOf(LdmlDeNotValidException.class)
        .satisfies(e -> {
          if (e instanceof LdmlDeNotValidException ldmlDeNotValidException) {
            assertThat(ldmlDeNotValidException.getErrors())
              .hasSize(2)
              .contains(
                new LdmlDeNotValidException.ValidationError(
                  URI.create("/errors/ldml-de-not-valid/cvc-pattern-valid"),
                  23,
                  64,
                  "cvc-pattern-valid: Value 'invalid-guid-to-break-xsd' is not facet-valid with respect to pattern '([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})|(\\{[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\\})' for type 'GUIDLiterals'."
                )
              )
              .contains(
                new LdmlDeNotValidException.ValidationError(
                  URI.create("/errors/ldml-de-not-valid/cvc-attribute.3"),
                  23,
                  64,
                  "cvc-attribute.3: The value 'invalid-guid-to-break-xsd' of attribute 'GUID' on element 'akn:meta' is not valid with respect to its type, 'GUIDLiterals'."
                )
              );
          }
        });
    }
  }

  @Nested
  class parseAndValidateRechtsetzungsdokument {

    @Test
    void itShouldParseAValidNorm() {
      // Given
      String xml = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/rechtsetzungsdokument-1.xml"
      );

      // When
      Rechtsetzungsdokument rechtsetzungsdokument =
        ldmlDeValidator.parseAndValidateRechtsetzungsdokument(xml);

      // Then
      // we can't use Norm::getEli as it is not yet namespace-aware
      assertThat(
        NodeParser.getValueFromMandatoryNodeFromExpression(
          "//*[local-name()='FRBRManifestation']/*[local-name()='FRBRthis']/@value",
          rechtsetzungsdokument.getDocument()
        )
      ).isEqualTo(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/rechtsetzungsdokument-1.xml"
      );
    }
  }
}
