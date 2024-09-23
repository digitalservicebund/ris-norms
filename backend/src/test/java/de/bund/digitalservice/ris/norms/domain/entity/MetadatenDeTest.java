package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MetadatenDeTest {

  @Test
  void getFna() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
                  <meta:fna>111-11-1</meta:fna>
              </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    assertThat(metadatenDe.getFna()).contains("111-11-1");
  }

  @Test
  void getFnaEmpty() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
              </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    assertThat(metadatenDe.getFna()).isEmpty();
  }

  @Test
  void getArt() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
                  <meta:art>test art</meta:art>
              </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    assertThat(metadatenDe.getArt()).contains("test art");
  }

  @Test
  void getArtEmpty() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
              </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    assertThat(metadatenDe.getArt()).isEmpty();
  }

  @Test
  void getTyp() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
                  <meta:typ>typi1</meta:typ>
              </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    assertThat(metadatenDe.getTyp()).contains("typi1");
  }

  @Test
  void getTypEmpty() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
              </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    assertThat(metadatenDe.getTyp()).isEmpty();
  }

  @Test
  void getRessort() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
              <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
              <meta:federfuehrung>
                  <meta:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                  <meta:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</meta:federfuehrend>
              </meta:federfuehrung>
          </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    assertThat(
      metadatenDe.getSimpleMetadatum(MetadatenDe.Metadata.RESSORT, LocalDate.parse("1990-01-01"))
    )
      .isEmpty();
    assertThat(
      metadatenDe.getSimpleMetadatum(MetadatenDe.Metadata.RESSORT, LocalDate.parse("2002-10-01"))
    )
      .contains("Bundesministerium der Justiz");
    assertThat(
      metadatenDe.getSimpleMetadatum(MetadatenDe.Metadata.RESSORT, LocalDate.parse("2022-12-01"))
    )
      .contains("Bundesministerium des Innern und für Heimat");
    assertThat(
      metadatenDe.getSimpleMetadatum(MetadatenDe.Metadata.RESSORT, LocalDate.parse("2024-06-18"))
    )
      .contains("Bundesministerium des Innern und für Heimat");
  }

  @Test
  void getRessortNotPresent() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
              </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    assertThat(metadatenDe.getSimpleMetadatum(MetadatenDe.Metadata.RESSORT, LocalDate.MAX))
      .isEmpty();
  }

  @Test
  void setRessortUpdate() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
              <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
              <meta:federfuehrung>
                  <meta:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                  <meta:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</meta:federfuehrend>
              </meta:federfuehrung>
          </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    final LocalDate atDate = LocalDate.parse("2002-10-01");
    assertThat(metadatenDe.getSimpleMetadatum(MetadatenDe.Metadata.RESSORT, atDate))
      .contains("Bundesministerium der Justiz");
    assertThat(metadatenDe.getNodes(MetadatenDe.Metadata.RESSORT.getXpath())).hasSize(2);

    metadatenDe.updateSimpleMetadatum(MetadatenDe.Metadata.RESSORT, atDate, "test ressort");

    assertThat(metadatenDe.getSimpleMetadatum(MetadatenDe.Metadata.RESSORT, atDate))
      .contains("test ressort");
    assertThat(metadatenDe.getNodes(MetadatenDe.Metadata.RESSORT.getXpath())).hasSize(2);
  }

  @Test
  void setRessortCreate() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
              <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7/">
              <meta:federfuehrung>
                  <meta:federfuehrend ab="2022-12-01" bis="unbestimmt">Bundesministerium des Innern und für Heimat</meta:federfuehrend>
                  <meta:federfuehrend ab="2002-10-01" bis="2022-11-30">Bundesministerium der Justiz</meta:federfuehrend>
              </meta:federfuehrung>
          </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    final LocalDate atDate = LocalDate.parse("1990-01-01");
    assertThat(metadatenDe.getSimpleMetadatum(MetadatenDe.Metadata.RESSORT, atDate)).isEmpty();
    assertThat(metadatenDe.getNodes(MetadatenDe.Metadata.RESSORT.getXpath())).hasSize(2);

    metadatenDe.updateSimpleMetadatum(MetadatenDe.Metadata.RESSORT, atDate, "test ressort");

    assertThat(metadatenDe.getSimpleMetadatum(MetadatenDe.Metadata.RESSORT, atDate))
      .contains("test ressort");
    assertThat(metadatenDe.getNodes(MetadatenDe.Metadata.RESSORT.getXpath())).hasSize(3);
  }
}
