package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class MetadatenDeTest {

  @Test
  void getFna() {
    final MetadatenDe metadatenDe = MetadatenDe
      .builder()
      .node(
        XmlMapper.toNode(
          """
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
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
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
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
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
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
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
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
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
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
                  <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
              </meta:legalDocML.de_metadaten>
          """
        )
      )
      .build();

    assertThat(metadatenDe.getTyp()).isEmpty();
  }
}
