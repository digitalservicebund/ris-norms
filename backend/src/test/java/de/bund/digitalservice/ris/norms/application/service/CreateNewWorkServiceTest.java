package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen.RahmenMetadata;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.UrlResource;

class CreateNewWorkServiceTest {

  final CreateNewWorkService createNewWorkService = new CreateNewWorkService();
  private final LdmlDeValidator ldmlDeValidator = new LdmlDeValidator(
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.8.1/schema/legalDocML.de.xsl")
      )
    ),
    Fixtures.getXsdSchemaService()
  );

  @Test
  void createNewWork() {
    // Given
    var verkuendung = Fixtures.loadNormFromDisk(
      "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24"
    );
    var verkuendungRegelungstext = verkuendung.getRegelungstext1();
    var embeddedNormRegelugnstext = verkuendung
      .getRegelungstexte()
      .stream()
      .filter(f -> f != verkuendungRegelungstext)
      .findFirst()
      .get();
    var embeddedNorm = Norm.builder().dokumente(Set.of(embeddedNormRegelugnstext)).build();
    var expressionEli = NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17-1/2020-01-01/1/deu");

    // When
    final Norm newWork = createNewWorkService.createNewWork(
      embeddedNorm,
      verkuendung.getRechtsetzungsdokument(),
      expressionEli
    );

    // Then
    newWork
      .getDokumente()
      .forEach(dokument -> {
        // Validate dokument
        assertThatCode(() ->
          ldmlDeValidator.validateXSDSchema(dokument)
        ).doesNotThrowAnyException();

        // Test FRBR elements
        final FRBRWork frbrWork = dokument.getMeta().getFRBRWork();
        final String subtype = frbrWork.getFRBRsubtype().get();
        assertThat(frbrWork.getEli()).hasToString(expressionEli.asWorkEli() + "/" + subtype);
        assertThat(frbrWork.getURI()).isEqualTo(expressionEli.asWorkEli().toUri());
        assertThat(frbrWork.getFRBRnumber()).hasValue(expressionEli.getNaturalIdentifier());
        assertThat(subtype).endsWith("-1");

        final FRBRExpression frbrExpression = dokument.getMeta().getFRBRExpression();
        assertThat(frbrExpression.getFRBRaliasPreviousVersionId()).isEmpty();
        assertThat(frbrExpression.getFRBRaliasNextVersionId()).isEmpty();
        assertThat(frbrExpression.getEli()).hasToString(expressionEli + "/" + subtype);
        assertThat(frbrExpression.getURI()).isEqualTo(expressionEli.toUri());
        assertThat(frbrExpression.getFBRDate()).isEqualTo(
          expressionEli.getPointInTime().toString()
        );

        final FRBRManifestation frbrManifestation = dokument.getMeta().getFRBRManifestation();
        final DokumentExpressionEli dokumentExpressionEli = DokumentExpressionEli.fromNormEli(
          expressionEli,
          subtype
        );
        final DokumentManifestationEli dokumentManifestationEli =
          DokumentManifestationEli.fromExpressionEli(
            dokumentExpressionEli,
            Norm.WORKING_COPY_DATE,
            dokument.getManifestationEli().getFormat()
          );
        assertThat(frbrManifestation.getEli()).isEqualTo(dokumentManifestationEli);
        assertThat(frbrManifestation.getURI()).isEqualTo(dokumentManifestationEli.toUri());
      });

    // Test regtxt metadata
    assertThat(newWork.getRahmenMetadata().getForm()).hasValue("stammform");

    // Test FNA and GESTA of redok metadata
    final RahmenMetadata rahmenMetadata = newWork.getRahmenMetadata();
    assertThat(rahmenMetadata.getFna()).isEqualTo(
      verkuendung.getRechtsetzungsdokument().getRahmenMetadata().getFna()
    );
    assertThat(rahmenMetadata.getGesta()).isEqualTo(
      verkuendung.getRechtsetzungsdokument().getRahmenMetadata().getGesta()
    );
  }

  @Test
  void createNewWorkSubtypingOffeneStruktur() {
    // Given
    var verkuendung = Fixtures.loadNormFromDisk(
      CreateNewWorkServiceTest.class,
      "amending-law-with-es-and-several-offene-strukturen"
    );
    var verkuendungRegelungstext = verkuendung.getRegelungstext1();
    var embeddedNormRegelugnstext = verkuendung
      .getRegelungstexte()
      .stream()
      .filter(f -> f != verkuendungRegelungstext)
      .findFirst()
      .get();
    Set<Dokument> dokumente = new HashSet<>();
    dokumente.add(embeddedNormRegelugnstext);
    embeddedNormRegelugnstext
      .getReferencedDokumentAndBinaryFileElis()
      .forEach(eli -> {
        verkuendung
          .getDokumente()
          .stream()
          .filter(dokument -> dokument.getManifestationEli().equals(eli))
          .findFirst()
          .ifPresent(dokumente::add);
      });
    var embeddedNorm = Norm.builder().dokumente(dokumente).build();
    var expressionEli = NormExpressionEli.fromString("eli/bund/bgbl-1/2024/17-1/2020-01-01/1/deu");

    // When
    final Norm newWork = createNewWorkService.createNewWork(
      embeddedNorm,
      verkuendung.getRechtsetzungsdokument(),
      expressionEli
    );

    // Then
    long numberOfOffeneStrukturen = newWork
      .getDokumente()
      .stream()
      .filter(OffeneStruktur.class::isInstance)
      .count();
    assertThat(numberOfOffeneStrukturen).isEqualTo(3);

    final List<Dokument> sortedOffeneStrukturen = newWork
      .getDokumente()
      .stream()
      .filter(OffeneStruktur.class::isInstance)
      .sorted(Comparator.comparing(Dokument::getManifestationEli))
      .toList();

    for (int i = 0; i < sortedOffeneStrukturen.size(); i++) {
      int subtypeNumber = i + 1;
      assertThat(
        sortedOffeneStrukturen.get(i).getMeta().getFRBRWork().getFRBRsubtype()
      ).hasValueSatisfying(subtype -> assertThat(subtype).endsWith(String.valueOf(subtypeNumber)));
    }
  }
}
