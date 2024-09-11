package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.UrlResource;

class LdmlDeValidatorTest {
  private final LdmlDeValidator ldmlDeValidator =
      new LdmlDeValidator(
          new UrlResource(
              Objects.requireNonNull(
                  LdmlDeValidator.class.getResource(
                      "/schema/fixtures/ldml1.6_ds_regelungstext.xsd"))));

  @Nested
  class parseAndValidate {
    @Test
    void itShouldParseAValidNorm() {
      // Given
      String xml = NormFixtures.loadTextFromDisk("NormWithMods.xml");

      // When
      Norm norm = ldmlDeValidator.parseAndValidate(xml);

      // Then
      // we can't use Norm::getEli as it is not yet namespace-aware
      assertThat(
              NodeParser.getValueFromMandatoryNodeFromExpression(
                  "//*[local-name()='FRBRManifestation']/*[local-name()='FRBRthis']/@value",
                  norm.getDocument()))
          .isEqualTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1.xml");
    }

    @Test
    void itShouldThrowForInvalidNorms() {
      // Given
      String xml = NormFixtures.loadTextFromDisk("NormWithModsXsdInvalid.xml");

      // When // Then
      assertThatThrownBy(() -> ldmlDeValidator.parseAndValidate(xml))
          .isInstanceOf(LdmlDeNotValidException.class)
          .satisfies(
              e -> {
                if (e instanceof LdmlDeNotValidException ldmlDeNotValidException) {
                  assertThat(ldmlDeNotValidException.getErrors())
                      .hasSize(3)
                      .contains(
                          new LdmlDeNotValidException.ValidationErrorMessage(
                              "/errors/ldml-de-not-valid/cvc-complex-type.4",
                              26,
                              79,
                              "cvc-complex-type.4: Attribute 'value' must appear on element 'akn:FRBRthis'."))
                      .contains(
                          new LdmlDeNotValidException.ValidationErrorMessage(
                              "/errors/ldml-de-not-valid/cvc-complex-type.4",
                              26,
                              79,
                              "cvc-complex-type.4: Attribute 'GUID' must appear on element 'akn:FRBRthis'."))
                      .contains(
                          new LdmlDeNotValidException.ValidationErrorMessage(
                              "/errors/ldml-de-not-valid/cvc-complex-type.2.4.b",
                              84,
                              34,
                              "cvc-complex-type.2.4.b: The content of element 'akn:identification' is not complete. One of '{\"http://Inhaltsdaten.LegalDocML.de/1.6/\":FRBRManifestation}' is expected."));
                }
              });
    }
  }
}
