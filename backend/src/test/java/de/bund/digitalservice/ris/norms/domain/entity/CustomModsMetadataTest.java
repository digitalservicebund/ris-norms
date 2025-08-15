package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CustomModsMetadataTest {

  @Nested
  class getOrCreateAmendedNormExpressions {

    @Test
    void create() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.2/'></norms:legalDocML.de_metadaten>
          """
        )
      );

      var amendedNormExpressions = customModsMetadata.getOrCreateAmendedNormExpressions();
      assertThat(amendedNormExpressions.getNormExpressions()).isEmpty();
    }

    @Test
    void get() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.2/">
              <norms:amended-norm-expressions>
                  <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
                  <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/2023/413/2024-01-11/2/deu</norms:norm-expression>
              </norms:amended-norm-expressions>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      var amendedNormExpressions = customModsMetadata.getAmendedNormExpressions();
      assertThat(amendedNormExpressions).isPresent();

      assertThat(
        amendedNormExpressions.get().getNormExpressions().getFirst().getNormExpressionEli()
      ).hasToString("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu");
      assertThat(
        amendedNormExpressions.get().getNormExpressions().getLast().getNormExpressionEli()
      ).hasToString("eli/bund/bgbl-1/2023/413/2024-01-11/2/deu");
    }
  }

  @Test
  void getZielnormenReferences() {
    var customModsMetadata = new CustomModsMetadata(
      toElement(
        """
        <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.2/'>
             <norms:zielnorm-references>
                 <norms:zielnorm-reference>
                     <norms:typ>Änderungsvorschrift</norms:typ>
                     <norms:geltungszeit>gz-1</norms:geltungszeit>
                     <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
                     <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
                 </norms:zielnorm-reference>
                 <norms:zielnorm-reference>
                     <norms:typ>Aufhebung</norms:typ>
                     <norms:geltungszeit>gz-2</norms:geltungszeit>
                     <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n2</norms:eid>
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
    assertThat(zielnormenReferences.get().stream().toList().getFirst().getEId()).hasToString(
      "art-z1_abs-n1_untergl-n1_listenelem-n1"
    );
    assertThat(zielnormenReferences.get().stream().toList().get(1).getEId()).hasToString(
      "art-z1_abs-n1_untergl-n1_listenelem-n2"
    );
  }

  @Nested
  class getOrCreateZielnormenReferences {

    @Test
    void create() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.2/'></norms:legalDocML.de_metadaten>
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
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.2/'>
               <norms:zielnorm-references>
                   <norms:zielnorm-reference>
                       <norms:typ>Änderungsvorschrift</norms:typ>
                       <norms:geltungszeit>gz-1</norms:geltungszeit>
                       <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
                       <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
                   </norms:zielnorm-reference>
                   <norms:zielnorm-reference>
                       <norms:typ>Aufhebung</norms:typ>
                       <norms:geltungszeit>gz-2</norms:geltungszeit>
                       <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n2</norms:eid>
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
      assertThat(zielnormenReferences.get().stream().toList().getFirst().getEId()).hasToString(
        "art-z1_abs-n1_untergl-n1_listenelem-n1"
      );
      assertThat(zielnormenReferences.get().stream().toList().get(1).getEId()).hasToString(
        "art-z1_abs-n1_untergl-n1_listenelem-n2"
      );
    }
  }

  @Nested
  class isZeitgrenzeInUse {

    @Test
    void itShouldReturnTrueWhenInUse() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.2/'>
               <norms:geltungszeiten>
                 <norms:geltungszeit id="gz-1" art="inkraft">2020-01-01</norms:geltungszeit>
               </norms:geltungszeiten>
               <norms:zielnorm-references>
                   <norms:zielnorm-reference>
                       <norms:typ>Änderungsvorschrift</norms:typ>
                       <norms:geltungszeit>gz-1</norms:geltungszeit>
                       <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
                       <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
                   </norms:zielnorm-reference>
               </norms:zielnorm-references>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      var zeitgrenze = customModsMetadata.getOrCreateGeltungszeiten().iterator().next();
      assertThat(customModsMetadata.isZeitgrenzeInUse(zeitgrenze)).isTrue();
    }

    @Test
    void itShouldReturnFalseWhenInUse() {
      var customModsMetadata = new CustomModsMetadata(
        toElement(
          """
          <norms:legalDocML.de_metadaten xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.2/'>
               <norms:geltungszeiten>
                 <norms:geltungszeit id="gz-1" art="inkraft">2020-01-01</norms:geltungszeit>
                 <norms:geltungszeit id="gz-2" art="inkraft">2022-01-01</norms:geltungszeit>
               </norms:geltungszeiten>
               <norms:zielnorm-references>
                   <norms:zielnorm-reference>
                       <norms:typ>Änderungsvorschrift</norms:typ>
                       <norms:geltungszeit>gz-2</norms:geltungszeit>
                       <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
                       <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
                   </norms:zielnorm-reference>
               </norms:zielnorm-references>
          </norms:legalDocML.de_metadaten>
          """
        )
      );

      var zeitgrenze = customModsMetadata.getOrCreateGeltungszeiten().iterator().next();
      assertThat(customModsMetadata.isZeitgrenzeInUse(zeitgrenze)).isFalse();
    }
  }
}
