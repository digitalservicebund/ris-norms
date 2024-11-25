package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EinzelelementTest {

  @Nested
  class getMetadatum {

    @Test
    void getArtDerNormAtDate() {
      final Einzelelement e = Einzelelement
        .builder()
        .node(
          XmlMapper.toNode(
            """
                        <ris:einzelelement xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/" href="#hauptteil-1_abschnitt-0_art-1">
                <ris:artDerNorm start="1990-01-01" end="1994-12-31">SN</ris:artDerNorm>
                <ris:artDerNorm start="1995-01-01" end="2000-12-31">ÄN</ris:artDerNorm>
                <ris:artDerNorm start="2001-01-01">ÜN</ris:artDerNorm>
            </ris:einzelelement>
            """
          )
        )
        .build();

      assertThat(
        e.getSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1980-01-01"))
      )
        .isEmpty();

      assertThat(
        e.getSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1990-01-01"))
      )
        .contains("SN");
      assertThat(
        e.getSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1992-01-01"))
      )
        .contains("SN");
      assertThat(
        e.getSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1994-12-31"))
      )
        .contains("SN");

      assertThat(
        e.getSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1995-01-01"))
      )
        .contains("ÄN");
      assertThat(
        e.getSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1998-01-01"))
      )
        .contains("ÄN");
      assertThat(
        e.getSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("2000-12-31"))
      )
        .contains("ÄN");

      assertThat(
        e.getSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("2001-01-01"))
      )
        .contains("ÜN");
      assertThat(
        e.getSimpleMetadatum(Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("2024-01-01"))
      )
        .contains("ÜN");
    }
  }
}
