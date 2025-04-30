package de.bund.digitalservice.ris.norms.domain.entity;

import static de.bund.digitalservice.ris.norms.utils.XmlMapper.toElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ZeitgrenzeTest {

  @Test
  void throwsExceptionWhenIdAttributeIsMissing() {
    var zeitgrenze = new Zeitgrenze(
      XmlMapper.toElement(
        """
        <norms:geltungszeit xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/" art="inkraft">2020-01-01</norms:geltungszeit>
        """
      ),
      null
    );

    assertThatThrownBy(zeitgrenze::getId)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Missing required attribute 'id' in <geltungszeit> node.");
  }

  @Test
  void throwsExceptionWhenArtAttributeIsMissing() {
    var zeitgrenze = new Zeitgrenze(
      XmlMapper.toElement(
        """
        <norms:geltungszeit xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/" id="gz-1">2020-01-01</norms:geltungszeit>
        """
      ),
      null
    );

    assertThatThrownBy(zeitgrenze::getArt)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(
        "Missing required attribute 'art' in <geltungszeit> node with id: gz-1"
      );
  }

  @Test
  void throwsExceptionWhenArtValueIsUnknown() {
    var zeitgrenze = new Zeitgrenze(
      XmlMapper.toElement(
        """
        <norms:geltungszeit xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/" id="gz-1" art="unknown">2020-01-01</norms:geltungszeit>
        """
      ),
      null
    );

    assertThatThrownBy(zeitgrenze::getArt)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Unknown art value: 'unknown'");
  }

  @Test
  void throwsExceptionWhenTextContentIsEmpty() {
    var zeitgrenze = new Zeitgrenze(
      XmlMapper.toElement(
        """
        <norms:geltungszeit xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/" id="gz-1" art="inkraft"></norms:geltungszeit>
        """
      ),
      null
    );

    assertThatThrownBy(zeitgrenze::getDate)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Missing text content in <geltungszeit> node with id: gz-1");
  }

  @Test
  void throwsExceptionWhenDateIsInvalid() {
    var zeitgrenze = new Zeitgrenze(
      XmlMapper.toElement(
        """
        <norms:geltungszeit xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/" id="gz-1" art="inkraft">2020-01-32</norms:geltungszeit>
        """
      ),
      null
    );

    assertThatThrownBy(zeitgrenze::getDate)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Invalid date format in <geltungszeit>: '2020-01-32'");
  }

  @Test
  void getId() {
    var zeitgrenze = new Zeitgrenze(
      XmlMapper.toElement(
        """
        <norms:geltungszeit xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/" id="gz-1" art="inkraft">2020-01-30</norms:geltungszeit>
        """
      ),
      null
    );

    assertThat(zeitgrenze.getId()).isEqualTo(new Zeitgrenze.Id("gz-1"));
  }

  @Test
  void getDate() {
    var zeitgrenze = new Zeitgrenze(
      XmlMapper.toElement(
        """
        <norms:geltungszeit xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/" id="gz-1" art="inkraft">2020-01-30</norms:geltungszeit>
        """
      ),
      null
    );

    assertThat(zeitgrenze.getDate()).isEqualTo(LocalDate.parse("2020-01-30"));
  }

  @Test
  void getArt() {
    var zeitgrenze = new Zeitgrenze(
      XmlMapper.toElement(
        """
        <norms:geltungszeit xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/" id="gz-1" art="inkraft">2020-01-30</norms:geltungszeit>
        """
      ),
      null
    );

    assertThat(zeitgrenze.getArt()).isEqualTo(Zeitgrenze.Art.INKRAFT);
  }

  @Test
  void isInUse() {
    var customModsMetadata = new CustomModsMetadata(
      toElement(
        """
        <norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
          <norms:geltungszeiten>
              <norms:geltungszeit id="gz-1" art="inkraft">2020-01-01</norms:geltungszeit>
              <norms:geltungszeit id="gz-2" art="ausserkraft">2024-12-12</norms:geltungszeit>
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

    assertThat(customModsMetadata.getZeitgrenzen().getFirst().isInUse()).isTrue();
    assertThat(customModsMetadata.getZeitgrenzen().get(1).isInUse()).isFalse();
  }
}
