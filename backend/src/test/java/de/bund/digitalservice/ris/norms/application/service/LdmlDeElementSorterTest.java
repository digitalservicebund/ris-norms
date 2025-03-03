package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.io.UrlResource;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

class LdmlDeElementSorterTest {

  private final LdmlDeElementSorter ldmlDeElementSorter = new LdmlDeElementSorter(
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.7.2/schema/legalDocML.de-baukasten.xsd")
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource("/LegalDocML.de/1.7.2/schema/legalDocML.de-metadaten.xsd")
      )
    ),
    new UrlResource(
      Objects.requireNonNull(
        LdmlDeValidator.class.getResource(
            "/LegalDocML.de/1.7.2/schema/legalDocML.de-regelungstextverkuendungsfassung.xsd"
          )
      )
    )
  );

  @Test
  void sort() {
    var elementNode =
      """
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="hauptteil-1_art-1"
                      GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                      refersTo="hauptaenderung">
                      <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                          GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                              Artikel 1 </akn:num>
                      <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                          GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes
                      </akn:heading>
                  </akn:article>
      """;
    var element = XmlMapper.toElement(elementNode);

    ldmlDeElementSorter.sortElements(element);

    final Diff diff = DiffBuilder
      .compare(Input.from(elementNode))
      .withTest(Input.from(element))
      .normalizeWhitespace()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void sort2() {
    var elementNode =
      """
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="hauptteil-1_art-1"
                      GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                      refersTo="hauptaenderung">
                      <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                          GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes
                      </akn:heading>
                      <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                          GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                              Artikel 1 </akn:num>
                  </akn:article>
      """;

    var expectedElementNode =
      """
          <akn:article xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="hauptteil-1_art-1"
                      GUID="cdbfc728-a070-42d9-ba2f-357945afef06"
                      period="#meta-1_geltzeiten-1_geltungszeitgr-1"
                      refersTo="hauptaenderung">
                      <akn:num eId="hauptteil-1_art-1_bezeichnung-1"
                          GUID="25a9acae-7463-4490-bc3f-8258b629d7e9">
                              Artikel 1 </akn:num>
                      <akn:heading eId="hauptteil-1_art-1_überschrift-1"
                          GUID="92827aa8-8118-4207-9f93-589345f0bab6">Änderung des Vereinsgesetzes
                      </akn:heading>
                  </akn:article>
      """;
    var element = XmlMapper.toElement(elementNode);

    ldmlDeElementSorter.sortElements(element);

    final Diff diff = DiffBuilder
      .compare(Input.from(expectedElementNode))
      .withTest(Input.from(element))
      .normalizeWhitespace()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @ParameterizedTest
  @ValueSource(
    strings = {
      "Einkommensteuer-Durchführungsverordnung.xml",
      "ReglungstextVerkuendungsfassung.xml",
      "SimpleOffenestruktur.xml",
      "Vereinsgesetz_2017_s419_2017-03-15.xml",
      "20240722_1108_Export_Regelungstext-Stammgesetz_Regelungstext-BDSG-Neufassung.xml",
    }
  )
  void correctlySortedNormsStayTheSame(String regelungstextFile) {
    var regelungstext = Fixtures.loadRegelungstextFromDisk(regelungstextFile);

    ldmlDeElementSorter.sortElements(regelungstext.getDocument().getDocumentElement());

    final Diff diff = DiffBuilder
      .compare(Input.from(Fixtures.loadRegelungstextFromDisk(regelungstextFile).getDocument()))
      .withTest(Input.from(regelungstext.getDocument()))
      .normalizeWhitespace()
      .ignoreComments()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }
}
