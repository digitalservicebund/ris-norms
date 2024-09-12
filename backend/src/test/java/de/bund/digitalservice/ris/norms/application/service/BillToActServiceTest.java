package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.application.port.input.BillToActUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

class BillToActServiceTest {
  final BillToActService underTest = new BillToActService();

  @Test
  void itChangesNothing() {
    // given
    final Norm norm = NormFixtures.loadFromDisk("NormWithMods.xml");

    // when
    Norm result = underTest.convert(new BillToActUseCase.Query(norm));

    // then
    final Diff diff =
        DiffBuilder.compare(Input.from(norm.getDocument()))
            .withTest(Input.from(result.getDocument()))
            .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void updateReferenceToAkomaNtoso() {
    // given
    final Norm norm = NormFixtures.loadFromDisk("ReglungstextEntwurfsfassung.xml");

    // when
    Norm result = underTest.convert(new BillToActUseCase.Query(norm));

    assertThat(result.getXsdLocation().getAknNameSpace())
        .isEqualTo("http://Inhaltsdaten.LegalDocML.de/1.7/");
    assertThat(result.getXsdLocation().getXsiSchemaLocation())
        .isEqualTo(
            "http://Metadaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-metadaten.xsd http://Inhaltsdaten.LegalDocML.de/1.7/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd");
  }
}
