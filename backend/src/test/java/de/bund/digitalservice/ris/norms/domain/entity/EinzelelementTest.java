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
      final Einzelelement e =
          Einzelelement.builder()
              .node(
                  XmlMapper.toNode(
                      """
                                <meta:einzelelement href="#hauptteil-1_abschnitt-0_para-1">
                                    <meta:artDerNorm start="1990-01-01" end="1994-12-31">SN</meta:artDerNorm>
                                    <meta:artDerNorm start="1995-01-01" end="2000-12-31">ÄN</meta:artDerNorm>
                                    <meta:artDerNorm start="2001-01-01">ÜN</meta:artDerNorm>
                                </meta:einzelelement>
                                """))
              .build();

      assertThat(
              e.getFrameSimpleMetadatum(
                  Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1980-01-01")))
          .isEmpty();

      assertThat(
              e.getFrameSimpleMetadatum(
                  Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1990-01-01")))
          .contains("SN");
      assertThat(
              e.getFrameSimpleMetadatum(
                  Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1992-01-01")))
          .contains("SN");
      assertThat(
              e.getFrameSimpleMetadatum(
                  Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1994-12-31")))
          .contains("SN");

      assertThat(
              e.getFrameSimpleMetadatum(
                  Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1995-01-01")))
          .contains("ÄN");
      assertThat(
              e.getFrameSimpleMetadatum(
                  Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("1998-01-01")))
          .contains("ÄN");
      assertThat(
              e.getFrameSimpleMetadatum(
                  Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("2000-12-31")))
          .contains("ÄN");

      assertThat(
              e.getFrameSimpleMetadatum(
                  Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("2001-01-01")))
          .contains("ÜN");
      assertThat(
              e.getFrameSimpleMetadatum(
                  Einzelelement.Metadata.ART_DER_NORM, LocalDate.parse("2024-01-01")))
          .contains("ÜN");
    }
  }
}
