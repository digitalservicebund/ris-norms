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
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.1/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
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
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.1/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
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
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.1/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
           <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    assertThat(zielnormReference.getEId()).hasToString("art-z1_abs-n1_untergl-n1_listenelem-n1");
  }

  @Test
  void getZielnorm() {
    var zielnormReference = new ZielnormReference(
      toElement(
        """
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.1/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
           <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    assertThat(zielnormReference.getZielnorm()).hasToString("eli/bund/bgbl-1/2021/123");
  }

  @Test
  void getNewWorkTrue() {
    // Given
    var zielnormReference = new ZielnormReference(
      toElement(
        """
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.1/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
           <norms:zielnorm new-work="true">eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    // When / Then
    assertThat(zielnormReference.isNewWork()).isTrue();
  }

  @Test
  void getNewWorkFalse() {
    // Given
    var zielnormReference = new ZielnormReference(
      toElement(
        """
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.1/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
           <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    // When / Then
    assertThat(zielnormReference.isNewWork()).isFalse();
  }

  @Test
  void setNewWorkFalse() {
    // Given
    var zielnormReference = new ZielnormReference(
      toElement(
        """
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.1/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
           <norms:zielnorm new-work="true">eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    // When
    zielnormReference.setNewWork(false);

    // Then
    assertThat(zielnormReference.isNewWork()).isFalse();
  }

  @Test
  void setNewWorkTrue() {
    // Given
    var zielnormReference = new ZielnormReference(
      toElement(
        """
        <norms:zielnorm-reference xmlns:norms='http://MetadatenMods.LegalDocML.de/1.8.1/'>
           <norms:typ>Änderungsvorschrift</norms:typ>
           <norms:geltungszeit>gz-1</norms:geltungszeit>
           <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
           <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        """
      )
    );

    // When
    zielnormReference.setNewWork(true);

    // Then
    assertThat(zielnormReference.isNewWork()).isTrue();
  }
}
