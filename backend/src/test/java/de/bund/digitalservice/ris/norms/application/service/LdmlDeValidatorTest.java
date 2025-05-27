package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.*;

import de.bund.digitalservice.ris.norms.application.exception.InvalidDokumentTypeException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeNotValidException;
import de.bund.digitalservice.ris.norms.application.exception.LdmlDeSchematronException;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
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
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.7.2/schema/legalDocML.de.xsl")
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
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml"
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
      ).isEqualTo("eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23/regelungstext-1.xml");
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

  @Nested
  class parseAndValidateDokument {

    @Test
    void itShouldParseAValidDokument() {
      // Given
      String xml = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/rechtsetzungsdokument-1.xml"
      );

      // When
      Dokument dokument = ldmlDeValidator.parseAndValidateDokument(
        "rechtsetzungsdokument-1.xml",
        xml
      );

      // Then
      // we can't use Norm::getEli as it is not yet namespace-aware
      assertThat(
        NodeParser.getValueFromMandatoryNodeFromExpression(
          "//*[local-name()='FRBRManifestation']/*[local-name()='FRBRthis']/@value",
          dokument.getDocument()
        )
      ).isEqualTo(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/rechtsetzungsdokument-1.xml"
      );
      assertThat(dokument).isInstanceOf(Rechtsetzungsdokument.class);
    }

    @Test
    void itShouldThrowForInvalidDokumentName() {
      String xml = Fixtures.loadTextFromDisk(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/rechtsetzungsdokument-1.xml"
      );

      assertThatThrownBy(() ->
        ldmlDeValidator.parseAndValidateDokument("unknown-dokument-name-1.xml", xml)
      ).isInstanceOf(InvalidDokumentTypeException.class);
    }
  }

  @Nested
  class validateSchematron {

    @Test
    void itShouldSuccessfullyValidateNorm() {
      // Given
      Norm norm = Fixtures.loadNormFromDisk(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/2022-08-23",
        true
      );

      // When // Then
      assertThatCode(() -> ldmlDeValidator.validateSchematron(norm)).doesNotThrowAnyException();
    }

    @Test
    void itShouldValidateAInvalidNormWithError() {
      // Given
      Norm norm = Fixtures.loadNormFromDisk(
        LdmlDeValidatorTest.class,
        "vereinsgesetz-schematron-invalid",
        true
      );

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
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}act[1]",
                  "Für ein Gesetz muss eine Eingangsformel verwendet werden.",
                  "",
                  "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
                )
              )
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}act[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}meta[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}temporalData[1]/@Q{}GUID",
                  "GUIDs müssen einmalig sein; \"0b03ee18-0131-47ec-bd46-519d60209cc7\" kommt jedoch 2-mal im Dokument vor!",
                  "meta-1_geltzeiten-1",
                  "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
                )
              )
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-00460-000",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}act[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}meta[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}temporalData[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}temporalGroup[1]/@Q{}GUID",
                  "GUIDs müssen einmalig sein; \"0b03ee18-0131-47ec-bd46-519d60209cc7\" kommt jedoch 2-mal im Dokument vor!",
                  "meta-1_geltzeiten-1_geltungszeitgr-1",
                  "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
                )
              )
              .contains(
                new LdmlDeSchematronException.ValidationError(
                  "/errors/ldml-de-not-schematron-valid/failed-assert/SCH-VERKF-hrefLiterals.expression.FRBRauthor",
                  "/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}akomaNtoso[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}act[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}meta[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}identification[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}FRBRExpression[1]/Q{http://Inhaltsdaten.LegalDocML.de/1.7.2/}FRBRauthor[1]/@Q{}href",
                  "In der Verkündungsfassung ist das Literal \"recht.bund.de/institution/bundestag\" an dieser Stelle nicht\n" +
                  "                                    zulässig. Erlaubt sind ausschließlich \"recht.bund.de/institution/bundesregierung\", \"recht.bund.de/institution/bundeskanzler\" sowie \"recht.bund.de/institution/bundespraesident\".",
                  "meta-1_ident-1_frbrexpression-1_frbrauthor-1",
                  "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml"
                )
              );
          }
        });
    }

    @Test
    void itShouldSuccessfullyValidateANormWithWarnings() {
      // Given
      Norm norm = Fixtures.loadNormFromDisk(
        LdmlDeValidatorTest.class,
        "vereinsgesetz-schematron-warning",
        true
      );

      // When // Then
      assertThatCode(() -> ldmlDeValidator.validateSchematron(norm)).doesNotThrowAnyException();
    }
  }
}
