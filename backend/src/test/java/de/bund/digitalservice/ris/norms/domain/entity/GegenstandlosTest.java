package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class GegenstandlosTest {

  @Test
  void getSinceDate() {
    var gegenstandslos = new Gegenstandlos(
      XmlMapper.toElement(
        """
        <meta:gegenstandslos xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/" seit="2024-01-01"/>
        """
      )
    );

    assertThat(gegenstandslos.getSinceDate()).isEqualTo(LocalDate.parse("2024-01-01"));
  }
}
