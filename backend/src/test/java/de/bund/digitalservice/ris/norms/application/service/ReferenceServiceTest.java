package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

class ReferenceServiceTest {

  final ReferenceService service = new ReferenceService();

  @Test
  void ifFindsAndCreatesReferences() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk(
      ReferenceServiceTest.class,
      "NormWithReferencesToFind.xml"
    );

    // When
    service.findAndCreateReferences(norm);

    // Then
    final Regelungstext expectedUpdatedRegelungstext = Fixtures.loadRegelungstextFromDisk(
      ReferenceServiceTest.class,
      "NormWithReferencesFound.xml"
    );
    final Diff diff = DiffBuilder.compare(
      Input.from(XmlMapper.toDocument(XmlMapper.toString(norm.getRegelungstext1().getDocument())))
    )
      .ignoreElementContentWhitespace()
      .withTest(Input.from(expectedUpdatedRegelungstext.getDocument()))
      .withAttributeFilter(attribute -> !attribute.getName().equals("GUID"))
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itDoesNotFindReferencesInNum() {
    // Given
    final Norm norm = Fixtures.loadNormFromDisk(
      ReferenceServiceTest.class,
      "NormWithReferencesInNumToSkip.xml"
    );
    // When
    service.findAndCreateReferences(norm);

    // Then
    final Regelungstext sameNormReload = Fixtures.loadRegelungstextFromDisk(
      ReferenceServiceTest.class,
      "NormWithReferencesInNumToSkip.xml"
    );
    final Diff diff = DiffBuilder.compare(Input.from(norm.getRegelungstext1().getDocument()))
      .withTest(Input.from(sameNormReload.getDocument()))
      .ignoreWhitespace()
      .withAttributeFilter(attribute -> !attribute.getName().equals("GUID"))
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }
}
