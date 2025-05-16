package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ZielnormReferenceTest {

  @Test
  void getTyp() {
    var zielnormReference = new ZielnormReference(
      toElement(
        """
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
           <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    assertThat(zielnormReference.getTyp()).isEqualTo("Änderungsvorschrift");
  }

  @Test
  void getGeltungszeit() {
    var zielnormReference = new ZielnormReference(
      toElement(
        """
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
           <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    assertThat(zielnormReference.getGeltungszeit()).hasToString("gz-1");
  }

  @Test
  void getEId() {
    var zielnormReference = new ZielnormReference(
      toElement(
        """
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
           <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    assertThat(zielnormReference.getEId()).hasToString(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"
    );
  }

  @Test
  void getZielnorm() {
    var zielnormReference = new ZielnormReference(
      toElement(
        """
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
           <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    assertThat(zielnormReference.getZielnorm()).hasToString("eli/bund/bgbl-1/2021/123");
  }
}
