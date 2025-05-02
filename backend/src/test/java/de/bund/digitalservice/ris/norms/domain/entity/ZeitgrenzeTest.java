package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.function.Function;
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
      mock()
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
      mock()
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
      mock()
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
      mock()
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
      mock()
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
      mock()
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
      mock()
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
      mock()
    );

    assertThat(zeitgrenze.getArt()).isEqualTo(Zeitgrenze.Art.INKRAFT);
  }

  @Test
  void isInUse() {
    Function<Zeitgrenze, Boolean> isInUse = mock();

    when(isInUse.apply(any())).thenReturn(true).thenReturn(false);

    var zeitgrenze = new Zeitgrenze(
      XmlMapper.toElement(
        """
        <norms:geltungszeit xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/" id="gz-1" art="inkraft">2020-01-30</norms:geltungszeit>
        """
      ),
      isInUse
    );

    assertThat(zeitgrenze.isInUse()).isTrue();
    assertThat(zeitgrenze.isInUse()).isFalse();

    verify(isInUse, times(2)).apply(zeitgrenze);
  }
}
