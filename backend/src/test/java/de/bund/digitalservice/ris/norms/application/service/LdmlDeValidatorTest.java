package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;

import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeSchematronException;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
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
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.6/schema/legalDocML.de.xsl")
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            "/LegalDocML.de/1.6/legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd"
          )
      )
    )
  );

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
          norm.getDocument()
        )
      )
        .isEqualTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1.xml");
    }

    @Test
    void itShouldThrowForInvalidNorms() {
      // Given
      String xml = NormFixtures.loadTextFromDisk("NormWithModsXsdInvalid.xml");

      // When // Then
      assertThatThrownBy(() -> ldmlDeValidator.parseAndValidate(xml))
        .isInstanceOf(LdmlDeNotValidException.class)
        .satisfies(e -> {
          if (e instanceof LdmlDeNotValidException ldmlDeNotValidException) {
            assertThat(ldmlDeNotValidException.getErrors())
              .hasSize(3)
              .contains(
                new LdmlDeNotValidException.ValidationError(
                  URI.create("/errors/ldml-de-not-valid/cvc-complex-type.4"),
                  26,
                  79,
                  "cvc-complex-type.4: Attribute 'value' must appear on element 'akn:FRBRthis'."
                )
              )
              .contains(
                new LdmlDeNotValidException.ValidationError(
                  URI.create("/errors/ldml-de-not-valid/cvc-complex-type.4"),
                  26,
                  79,
                  "cvc-complex-type.4: Attribute 'GUID' must appear on element 'akn:FRBRthis'."
                )
              )
              .contains(
                new LdmlDeNotValidException.ValidationError(
                  URI.create("/errors/ldml-de-not-valid/cvc-complex-type.2.4.b"),
                  84,
                  34,
                  "cvc-complex-type.2.4.b: The content of element 'akn:identification' is not complete. One of '{\"http://Inhaltsdaten.LegalDocML.de/1.6/\":FRBRManifestation}' is expected."
                )
              );
          }
        });
    }
  }

  @Nested
  class validateSchematron {

    @Test
    void itShouldValidateNorm() {
      // Given
      Norm norm = NormFixtures.loadFromDisk("NormWithMods.xml", true);

      // When // Then
      assertThatCode(() -> ldmlDeValidator.validateSchematron(norm)).doesNotThrowAnyException();
    }

    @Test
    void itShouldValidateAInvalidNorm() {
      // Given
      Norm norm = NormFixtures.loadFromDisk("NormWithModsSchematronInvalid.xml", true);

      // When // Then
      assertThatThrownBy(() -> ldmlDeValidator.validateSchematron(norm))
        .isInstanceOf(LdmlDeSchematronException.class)
        .satisfies(e -> {
          if (e instanceof LdmlDeSchematronException ldmlDeSchematronException) {
            assertThat(ldmlDeSchematronException.getErrors())
              .hasSize(3)
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00050-005",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}act[1]",
                  "Für ein Gesetz muss eine Eingangsformel verwendet werden.",
                  ""
                )
              )
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}act[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}meta[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}temporalData[1]/@Q{}GUID",
                  "GUIDs müssen einmalig sein; \"82854d32-d922-43d7-ac8c-612c07219336\" kommt jedoch 2-mal im Dokument vor!",
                  "meta-1_geltzeiten-1"
                )
              )
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}act[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}meta[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}temporalData[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.6/}temporalGroup[1]/@Q{}GUID",
                  "GUIDs müssen einmalig sein; \"82854d32-d922-43d7-ac8c-612c07219336\" kommt jedoch 2-mal im Dokument vor!",
                  "meta-1_geltzeiten-1_geltungszeitgr-1"
                )
              );
          }
        });
    }
  }
}
