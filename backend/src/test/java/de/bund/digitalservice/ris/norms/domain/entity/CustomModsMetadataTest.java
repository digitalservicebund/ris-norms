package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

class CustomModsMetadataTest {

  @Nested
  class getAmendedNormExpressionElis {

    @Test
    void returnNormExpressionElis() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
              <norms:amended-norm-expressions>
                  <norms:norm-expression>eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
                  <norms:norm-expression>eli/bund/bgbl-1/2023/413/2024-01-11/2/deu</norms:norm-expression>
                  <norms:norm-expression>eli/bund/bgbl-1/2023/413/2024-02-12/1/deu</norms:norm-expression>
                  <norms:norm-expression>eli/bund/bgbl-1/2023/413/0001-01-01/24/deu</norms:norm-expression>
                  <norms:norm-expression>eli/bund/bgbl-1/2002/s1181/2023-12-29/1/deu</norms:norm-expression>
              </norms:amended-norm-expressions>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      assertThat(customModsMetadata.getAmendedNormExpressionElis())
        .hasSize(5)
        .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu"))
        .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2024-01-11/2/deu"))
        .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/2024-02-12/1/deu"))
        .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2023/413/0001-01-01/24/deu"))
        .contains(NormExpressionEli.fromString("eli/bund/bgbl-1/2002/s1181/2023-12-29/1/deu"));
    }
  }

  @Nested
  class getZeitgrenzen {

    @Test
    void returnsEmptyListWhenNoGeltungszeitNodes() {
      var xmlWithNoGeltungszeiten =
        """
            <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
                <norms:geltungszeiten>
                    <!-- No <geltungszeit> nodes here -->
                </norms:geltungszeiten>
            </norms:legalDocML.de_metadaten>
        """;

      var customModsMetadata = new CustomModsMetadata(toElement(xmlWithNoGeltungszeiten));

      // Ensure the list is empty
      assertThat(customModsMetadata.getZeitgrenzen()).isEmpty();
    }

    @Test
    void returnsZeitgrenzen() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
            <norms:geltungszeiten>
                <norms:geltungszeit id="gz-1" art="inkraft">2020-01-01</norms:geltungszeit>
                <norms:geltungszeit id="gz-2" art="ausserkraft">2024-12-12</norms:geltungszeit>
                <norms:geltungszeit id="gz-3" art="inkraft">2025-01-01</norms:geltungszeit>
                <norms:geltungszeit id="gz-4" art="ausserkraft">2026-12-12</norms:geltungszeit>
            </norms:geltungszeiten>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      assertThat(customModsMetadata.getZeitgrenzen())
        .hasSize(4)
        .anyMatch(z ->
          z.getId().equals("gz-1") &&
          z.getArt() == Zeitgrenze.Art.INKRAFT &&
          z.getDate().equals(LocalDate.parse("2020-01-01"))
        )
        .anyMatch(z ->
          z.getId().equals("gz-2") &&
          z.getArt() == Zeitgrenze.Art.AUSSERKRAFT &&
          z.getDate().equals(LocalDate.parse("2024-12-12"))
        )
        .anyMatch(z ->
          z.getId().equals("gz-3") &&
          z.getArt() == Zeitgrenze.Art.INKRAFT &&
          z.getDate().equals(LocalDate.parse("2025-01-01"))
        )
        .anyMatch(z ->
          z.getId().equals("gz-4") &&
          z.getArt() == Zeitgrenze.Art.AUSSERKRAFT &&
          z.getDate().equals(LocalDate.parse("2026-12-12"))
        );
    }

    @Test
    void throwsExceptionWhenIdAttributeIsMissing() {
      var xmlWithMissingId =
        """
            <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
                <norms:geltungszeiten>
                    <norms:geltungszeit art="inkraft">2020-01-01</norms:geltungszeit>
                </norms:geltungszeiten>
            </norms:legalDocML.de_metadaten>
        """;

      var customModsMetadata = new CustomModsMetadata(toElement(xmlWithMissingId));

      assertThatThrownBy(customModsMetadata::getZeitgrenzen)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Missing required attribute 'id' in <geltungszeit> node.");
    }

    @Test
    void throwsExceptionWhenArtAttributeIsMissing() {
      var xmlWithMissingArt =
        """
            <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
                <norms:geltungszeiten>
                    <norms:geltungszeit id="gz-1">2020-01-01</norms:geltungszeit>
                </norms:geltungszeiten>
            </norms:legalDocML.de_metadaten>
        """;

      var customModsMetadata = new CustomModsMetadata(toElement(xmlWithMissingArt));

      assertThatThrownBy(customModsMetadata::getZeitgrenzen)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(
          "Missing required attribute 'art' in <geltungszeit> node with id: gz-1"
        );
    }

    @Test
    void throwsExceptionWhenArtValueIsUnknown() {
      var xmlWithUnknownArt =
        """
            <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
                <norms:geltungszeiten>
                    <norms:geltungszeit id="gz-1" art="unknown">2020-01-01</norms:geltungszeit>
                </norms:geltungszeiten>
            </norms:legalDocML.de_metadaten>
        """;

      var customModsMetadata = new CustomModsMetadata(toElement(xmlWithUnknownArt));

      assertThatThrownBy(customModsMetadata::getZeitgrenzen)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unknown art value: 'unknown'");
    }

    @Test
    void throwsExceptionWhenTextContentIsEmpty() {
      var xmlWithEmptyTextContent =
        """
            <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
                <norms:geltungszeiten>
                    <norms:geltungszeit id="gz-1" art="inkraft"></norms:geltungszeit>
                </norms:geltungszeiten>
            </norms:legalDocML.de_metadaten>
        """;

      var customModsMetadata = new CustomModsMetadata(toElement(xmlWithEmptyTextContent));

      assertThatThrownBy(customModsMetadata::getZeitgrenzen)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Missing text content in <geltungszeit> node with id: gz-1");
    }

    @Test
    void throwsExceptionWhenDateIsInvalid() {
      var xmlWithInvalidDate =
        """
            <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
                <norms:geltungszeiten>
                    <norms:geltungszeit id="gz-1" art="inkraft">2020-01-32</norms:geltungszeit>
                </norms:geltungszeiten>
            </norms:legalDocML.de_metadaten>
        """;

      var customModsMetadata = new CustomModsMetadata(toElement(xmlWithInvalidDate));

      assertThatThrownBy(customModsMetadata::getZeitgrenzen)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid date format in <geltungszeit>: '2020-01-32'");
    }
  }

  @Nested
  class updateZeitgrenzen {

    @Test
    void updatesZeitgrenzen() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
            <norms:geltungszeiten>
                <norms:geltungszeit id="gz-1" art="inkraft">2020-01-01</norms:geltungszeit>
                <norms:geltungszeit id="gz-2" art="ausserkraft">2024-12-12</norms:geltungszeit>
                <norms:geltungszeit id="gz-3" art="inkraft">2025-01-01</norms:geltungszeit>
                <norms:geltungszeit id="gz-4" art="ausserkraft">2026-12-12</norms:geltungszeit>
            </norms:geltungszeiten>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      final List<Zeitgrenze> newZeitgrenzen = List.of(
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.INKRAFT)
          .date(LocalDate.parse("2023-05-01"))
          .build(),
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.AUSSERKRAFT)
          .date(LocalDate.parse("2024-06-15"))
          .build(),
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.INKRAFT)
          .date(LocalDate.parse("2025-02-20"))
          .build(),
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.AUSSERKRAFT)
          .date(LocalDate.parse("2026-11-30"))
          .build()
      );
      final List<Zeitgrenze> updatedZeitgrenzen = customModsMetadata.updateZeitgrenzen(
        newZeitgrenzen
      );

      assertThat(updatedZeitgrenzen)
        .hasSize(4)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactly(
          tuple("gz-1", LocalDate.parse("2023-05-01"), Zeitgrenze.Art.INKRAFT),
          tuple("gz-2", LocalDate.parse("2024-06-15"), Zeitgrenze.Art.AUSSERKRAFT),
          tuple("gz-3", LocalDate.parse("2025-02-20"), Zeitgrenze.Art.INKRAFT),
          tuple("gz-4", LocalDate.parse("2026-11-30"), Zeitgrenze.Art.AUSSERKRAFT)
        );

      // Additionally, verify that the old Zeitgrenze tuples are no longer present.
      assertThat(customModsMetadata.getZeitgrenzen())
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .doesNotContain(
          tuple("gz-1", LocalDate.parse("2020-01-01"), Zeitgrenze.Art.INKRAFT),
          tuple("gz-2", LocalDate.parse("2024-12-12"), Zeitgrenze.Art.AUSSERKRAFT),
          tuple("gz-3", LocalDate.parse("2025-01-01"), Zeitgrenze.Art.INKRAFT),
          tuple("gz-4", LocalDate.parse("2026-12-12"), Zeitgrenze.Art.AUSSERKRAFT)
        );
    }

    @Test
    void updatesZeitgrenzenwhithEmptyList() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
              <norms:geltungszeiten>
                <norms:geltungszeit id="gz-1" art="inkraft">2020-01-01</norms:geltungszeit>
                <norms:geltungszeit id="gz-2" art="ausserkraft">2024-12-12</norms:geltungszeit>
                <norms:geltungszeit id="gz-3" art="inkraft">2025-01-01</norms:geltungszeit>
                <norms:geltungszeit id="gz-4" art="ausserkraft">2026-12-12</norms:geltungszeit>
              </norms:geltungszeiten>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      final List<Zeitgrenze> newZeitgrenzen = Collections.emptyList();
      final List<Zeitgrenze> updatedZeitgrenzen = customModsMetadata.updateZeitgrenzen(
        newZeitgrenzen
      );
      final List<Zeitgrenze> newlyRetrievedZeitgrenzen = customModsMetadata.getZeitgrenzen();
      assertThat(updatedZeitgrenzen).isEmpty();
      assertThat(newlyRetrievedZeitgrenzen).isEmpty();

      final Optional<Node> geltunsZeitenNode = NodeParser.getNodeFromExpression(
        "./geltungszeiten",
        customModsMetadata.getElement()
      );
      assertThat(geltunsZeitenNode).isNotPresent();
    }

    @Test
    void updatesZeitgrenzenWithUnsortedDates() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
               <norms:geltungszeiten/>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      final List<Zeitgrenze> newZeitgrenzen = List.of(
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.INKRAFT)
          .date(LocalDate.parse("2025-02-20"))
          .build(),
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.AUSSERKRAFT)
          .date(LocalDate.parse("2023-05-01"))
          .build(),
        Zeitgrenze.builder().art(Zeitgrenze.Art.INKRAFT).date(LocalDate.parse("2024-06-15")).build()
      );

      final List<Zeitgrenze> updatedZeitgrenzen = customModsMetadata.updateZeitgrenzen(
        newZeitgrenzen
      );

      assertThat(updatedZeitgrenzen).extracting(Zeitgrenze::getDate).isSorted();

      assertThat(updatedZeitgrenzen)
        .hasSize(3)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactly(
          tuple("gz-1", LocalDate.parse("2023-05-01"), Zeitgrenze.Art.AUSSERKRAFT),
          tuple("gz-2", LocalDate.parse("2024-06-15"), Zeitgrenze.Art.INKRAFT),
          tuple("gz-3", LocalDate.parse("2025-02-20"), Zeitgrenze.Art.INKRAFT)
        );
    }

    @Test
    void updatesZeitgrenzenWithNotParentPresent() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      final List<Zeitgrenze> newZeitgrenzen = List.of(
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.AUSSERKRAFT)
          .date(LocalDate.parse("2023-05-01"))
          .build(),
        Zeitgrenze
          .builder()
          .art(Zeitgrenze.Art.INKRAFT)
          .date(LocalDate.parse("2024-06-15"))
          .build(),
        Zeitgrenze.builder().art(Zeitgrenze.Art.INKRAFT).date(LocalDate.parse("2025-02-20")).build()
      );

      final List<Zeitgrenze> updatedZeitgrenzen = customModsMetadata.updateZeitgrenzen(
        newZeitgrenzen
      );

      assertThat(updatedZeitgrenzen)
        .hasSize(3)
        .extracting(Zeitgrenze::getId, Zeitgrenze::getDate, Zeitgrenze::getArt)
        .containsExactly(
          tuple("gz-1", LocalDate.parse("2023-05-01"), Zeitgrenze.Art.AUSSERKRAFT),
          tuple("gz-2", LocalDate.parse("2024-06-15"), Zeitgrenze.Art.INKRAFT),
          tuple("gz-3", LocalDate.parse("2025-02-20"), Zeitgrenze.Art.INKRAFT)
        );
    }
  }

  @Test
  void getZielnormenReferences() {
    var customModsMetadata = new CustomModsMetadata(
      toElement(
        """
        <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
             <norms:zielnorm-references>
                 <norms:zielnorm-reference>
                     <norms:typ>Änderungsvorschrift</norms:typ>
                     <norms:geltungszeit>gz-1</norms:geltungszeit>
                     <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
                     <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
                 </norms:zielnorm-reference>
                 <norms:zielnorm-reference>
                     <norms:typ>Aufhebung</norms:typ>
                     <norms:geltungszeit>gz-2</norms:geltungszeit>
                     <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-2</norms:eid>
                     <norms:zielnorm>eli/bund/bgbl-1/2019/789</norms:zielnorm>
                 </norms:zielnorm-reference>
             </norms:zielnorm-references>
        </norms:legalDocML.de_metadaten>
        """
      )
    );

    var zielnormenReferences = customModsMetadata.getZielnormenReferences();
    assertThat(zielnormenReferences).hasSize(2);
    assertThat(zielnormenReferences.getFirst().getEId())
      .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1");
    assertThat(zielnormenReferences.get(1).getEId())
      .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2");
  }
}
