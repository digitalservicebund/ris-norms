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
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.7/schema/legalDocML.de.xsl")
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            "/LegalDocML.de/1.7/legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd"
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
        .isEqualTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml");
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
                  24,
                  74,
                  "cvc-complex-type.4: Attribute 'value' must appear on element 'akn:FRBRthis'."
                )
              )
              .contains(
                new LdmlDeNotValidException.ValidationError(
                  URI.create("/errors/ldml-de-not-valid/cvc-complex-type.4"),
                  24,
                  74,
                  "cvc-complex-type.4: Attribute 'GUID' must appear on element 'akn:FRBRthis'."
                )
              )
              .contains(
                new LdmlDeNotValidException.ValidationError(
                  URI.create("/errors/ldml-de-not-valid/cvc-complex-type.2.4.b"),
                  82,
                  31,
                  "cvc-complex-type.2.4.b: The content of element 'akn:identification' is not complete. One of '{\"http://Inhaltsdaten.LegalDocML.de/1.7/\":FRBRManifestation}' is expected."
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
              .hasSize(4)
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00050-005",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}act[1]",
                  "Für ein Gesetz muss eine Eingangsformel verwendet werden.",
                  ""
                )
              )
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}act[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}meta[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}temporalData[1]/@Q{}GUID",
                  "GUIDs müssen einmalig sein; \"82854d32-d922-43d7-ac8c-612c07219336\" kommt jedoch 2-mal im Dokument vor!",
                  "meta-1_geltzeiten-1"
                )
              )
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}act[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}meta[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}temporalData[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}temporalGroup[1]/@Q{}GUID",
                  "GUIDs müssen einmalig sein; \"82854d32-d922-43d7-ac8c-612c07219336\" kommt jedoch 2-mal im Dokument vor!",
                  "meta-1_geltzeiten-1_geltungszeitgr-1"
                )
              )
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-VERKF-hrefLiterals.expression.FRBRauthor",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}act[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}meta[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}identification[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}FRBRExpression[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7/}FRBRauthor[1]/@Q{}href",
                  "In der Verkündungsfassung ist das Literal \"recht.bund.de/institution/bundestag\" an dieser Stelle nicht\n" +
                  "                                    zulässig. Erlaubt sind ausschließlich \"recht.bund.de/institution/bundesregierung\", \"recht.bund.de/institution/bundeskanzler\" sowie \"recht.bund.de/institution/bundespraesident\".",
                  "meta-1_ident-1_frbrexpression-1_frbrauthor-1"
                )
              );
          }
        });
    }
  }
}
