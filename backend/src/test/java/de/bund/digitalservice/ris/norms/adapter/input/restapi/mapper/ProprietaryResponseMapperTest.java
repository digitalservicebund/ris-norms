package de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ProprietaryResponseMapperTest {

  @Test
  void convertsProprietaryToResponseSchema() {
    final Proprietary proprietary = Proprietary
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                  <meta:typ>gesetz</meta:typ>
                  <meta:fna>000-00-0</meta:fna>
                  <meta:fassung>verkuendungsfassung</meta:fassung>
              </meta:legalDocML.de_metadaten>
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/">
                  <ris:fna start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
                  <ris:fna start="1995-01-01" end="2000-12-31">222-22-2</ris:fna>
                  <ris:fna start="2001-01-01">333-33-3</ris:fna>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      )
      .build();

    // When
    var responseSchema = ProprietaryResponseMapper.fromProprietary(
      proprietary,
      LocalDate.parse("1999-09-09")
    );

    // Then
    assertThat(responseSchema.getFna()).isEqualTo("222-22-2");
  }

  @Test
  void convertsProprietaryEinzelelementToResponseSchema() {
    final Proprietary proprietary = Proprietary
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <akn:proprietary xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
              <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.1/">
                          <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                      <ris:artDerNorm start="1990-01-01" end="1994-12-31">SN</ris:artDerNorm>
                      <ris:artDerNorm start="1995-01-01" end="2000-12-31">ÄN</ris:artDerNorm>
                      <ris:artDerNorm start="2001-01-01">ÜN</ris:artDerNorm>
                  </ris:einzelelement>
              </ris:legalDocML.de_metadaten>
          </akn:proprietary>
          """
        )
      )
      .build();

    // When
    var responseSchema = ProprietaryResponseMapper.fromProprietarySingleElement(
      proprietary,
      "hauptteil-1_abschnitt-0_art-1",
      LocalDate.parse("1999-09-09")
    );

    // Then
    assertThat(responseSchema.getArtDerNorm()).isEqualTo("ÄN");
  }
}
