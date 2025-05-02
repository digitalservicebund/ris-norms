package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

      assertThat(customModsMetadata.getZeitgrenzen())
        .hasSize(4)
        .anyMatch(z ->
          z.getId().toString().equals("gz-1") &&
          z.getArt() == Zeitgrenze.Art.INKRAFT &&
          z.getDate().equals(LocalDate.parse("2020-01-01")) &&
          z.isInUse()
        )
        .anyMatch(z ->
          z.getId().toString().equals("gz-2") &&
          z.getArt() == Zeitgrenze.Art.AUSSERKRAFT &&
          z.getDate().equals(LocalDate.parse("2024-12-12")) &&
          z.isInUse()
        )
        .anyMatch(z ->
          z.getId().toString().equals("gz-3") &&
          z.getArt() == Zeitgrenze.Art.INKRAFT &&
          z.getDate().equals(LocalDate.parse("2025-01-01")) &&
          !z.isInUse()
        )
        .anyMatch(z ->
          z.getId().toString().equals("gz-4") &&
          z.getArt() == Zeitgrenze.Art.AUSSERKRAFT &&
          z.getDate().equals(LocalDate.parse("2026-12-12")) &&
          !z.isInUse()
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
    assertThat(zielnormenReferences).isPresent();

    assertThat(zielnormenReferences.get()).hasSize(2);
    assertThat(zielnormenReferences.get().stream().toList().getFirst().getEId())
      .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1");
    assertThat(zielnormenReferences.get().stream().toList().get(1).getEId())
      .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2");
  }

  @Nested
  class getOrCreateZielnormenReferences {

    @Test
    void create() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'></norms:legalDocML.de_metadaten>
          """
        )
      );

      var zielnormenReferences = customModsMetadata.getOrCreateZielnormenReferences();
      assertThat(zielnormenReferences).isEmpty();
    }

    @Test
    void get() {
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
      assertThat(zielnormenReferences).isPresent();

      assertThat(zielnormenReferences.get()).hasSize(2);
      assertThat(zielnormenReferences.get().stream().toList().getFirst().getEId())
        .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-1");
      assertThat(zielnormenReferences.get().stream().toList().get(1).getEId())
        .hasToString("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2");
    }
  }

  @Nested
  class isZeitgrenzeInUse {

    @Test
    void itShouldReturnTrueWhenInUse() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
               <norms:geltungszeiten>
                 <norms:geltungszeit id="gz-1" art="inkraft">2020-01-01</norms:geltungszeit>
               </norms:geltungszeiten>
               <norms:zielnorm-references>
                   <norms:zielnorm-reference>
                       <norms:typ>Änderungsvorschrift</norms:typ>
                       <norms:geltungszeit>gz-1</norms:geltungszeit>
                       <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
                       <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
                   </norms:zielnorm-reference>
               </norms:zielnorm-references>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      var zeitgrenze = customModsMetadata.getZeitgrenzen().getFirst();
      assertThat(customModsMetadata.isZeitgrenzeInUse(zeitgrenze)).isTrue();
    }

    @Test
    void itShouldReturnFalseWhenInUse() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
               <norms:geltungszeiten>
                 <norms:geltungszeit id="gz-1" art="inkraft">2020-01-01</norms:geltungszeit>
                 <norms:geltungszeit id="gz-2" art="inkraft">2022-01-01</norms:geltungszeit>
               </norms:geltungszeiten>
               <norms:zielnorm-references>
                   <norms:zielnorm-reference>
                       <norms:typ>Änderungsvorschrift</norms:typ>
                       <norms:geltungszeit>gz-2</norms:geltungszeit>
                       <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
                       <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
                   </norms:zielnorm-reference>
               </norms:zielnorm-references>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      var zeitgrenze = customModsMetadata.getZeitgrenzen().getFirst();
      assertThat(customModsMetadata.isZeitgrenzeInUse(zeitgrenze)).isFalse();
    }
  }
}
